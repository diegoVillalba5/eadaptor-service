package com.eadaptorservice.controller;

import com.eadaptorservice.dto.soap.Envelope;
import com.eadaptorservice.service.EAdaptorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("eadaptor")
@RequiredArgsConstructor
public class EAdaptorController {
    private final EAdaptorService eAdaptorService;

    private static final Logger logger = LoggerFactory.getLogger(EAdaptorController.class);

    @GetMapping(produces = "text/xml")
    public ResponseEntity<String> ping() {
        String response = """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                        <soap:Body>
                            <PingResponse>Eadaptor Service is alive</PingResponse>
                        </soap:Body>
                    </soap:Envelope>
                """;
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = "text/xml")
    public ResponseEntity<Object> eAdaptor(
            HttpServletRequest request,
            @RequestBody Envelope body
    ) {
        try {
            return ResponseEntity.ok(eAdaptorService.processEadaptorRequest(request, body));
        } catch (HttpClientErrorException e) {
            logger.error("Error processing request: {}", e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            logger.error("Error processing request: {} {}", e, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
