package com.eadaptorservice.dto.soap;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @XmlElement(name = "SendStreamRequest", namespace = "http://CargoWise.com/eHub/2010/06")
    private SendStreamRequest sendStreamRequest;

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SendStreamRequest {
        @XmlElement(name = "Payload", namespace = "http://CargoWise.com/eHub/2010/06")
        private Payload payload;

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    @Setter
    @ToString
    public static class Payload {
        @XmlElement(name = "Message", namespace = "http://CargoWise.com/eHub/2010/06")
        private List<Message> message;
    }

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Message {
        @XmlAttribute(name = "ApplicationCode")
        private String xmlns;

        @XmlAttribute(name = "ClientID")
        private String clientID;

        @XmlAttribute(name = "TrackingID")
        private String trackingID;

        @XmlAttribute(name = "SchemaName")
        private String schemaName;

        @XmlAttribute(name = "SchemaType")
        private String schemaType;

        @XmlAttribute(name = "EmailSubject")
        private String emailSubject;

        @XmlAttribute(name = "FileName")
        private String fileName;

        @XmlValue
        private String value;
    }
}
