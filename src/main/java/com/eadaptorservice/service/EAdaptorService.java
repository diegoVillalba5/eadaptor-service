package com.eadaptorservice.service;

import com.eadaptorservice.dto.soap.Body;
import com.eadaptorservice.dto.soap.Envelope;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EAdaptorService {
    @Value("${eadaptor.password}")
    private String password;

    @Value("${eadaptor.outbound.folder}")
    private String outboundFolder;

    private static final Logger logger = LoggerFactory.getLogger(EAdaptorService.class);

    public Object processEadaptorRequest(HttpServletRequest request, Envelope body) {
        String soapAction = request.getHeader("SOAPAction");

        List<Body.Message> messages = body.getBody().getSendStreamRequest().getPayload().getMessage();
        for (Body.Message message : messages) {
            logger.info("SOAP Action: {} | Envelope username: {} | Envelope password: {} | Received From Client ID: {} | eHub Tracking ID: {} | IP Address: {}",
                    soapAction,
                    body.getHeader().getSecurity().getUsernameToken().getUsername(),
                    body.getHeader().getSecurity().getUsernameToken().getPassword().getValue(),
                    message.getClientID(),
                    message.getTrackingID(),
                    request.getRemoteAddr()
            );
            if (!Objects.equals(password, body.getHeader().getSecurity().getUsernameToken().getPassword().getValue())) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }

            String fileName = format("%s_%s.xml",
                    body.getHeader().getSecurity().getUsernameToken().getUsername(),
                    message.getTrackingID()
            );

            try {
                decodeAndSaveFile(fileName, message.getValue());
                logger.info("File with name {} saved at {}", fileName, outboundFolder);
            } catch (IOException e) {
                logger.error("Error saving file: {}", e.getMessage());
                return "Error processing request.";
            }
        }

        LocalDateTime created = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime expires = created.plusHours(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Envelope response = new Envelope();
        response.getHeader().getSecurity().getTimestamp().setCreated(created.format(formatter));
        response.getHeader().getSecurity().getTimestamp().setExpires(expires.format(formatter));

        return response;
    }

    private void decodeAndSaveFile(String fileName, String encodedFile) throws IOException {
        byte[] gzippedData = Base64.getDecoder().decode(encodedFile);

        try (InputStream byteArrayInputStream = new ByteArrayInputStream(gzippedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             FileOutputStream fos = new FileOutputStream(Path.of(outboundFolder, fileName).toFile())) {

            byte[] buffer = new byte[81920];
            int bytesRead;

            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.flush();
        }
    }
}
