package com.eadaptorservice.dto.soap;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Header {
    @XmlElement(name = "SendStreamRequestTrackingID", namespace = "http://CargoWise.com/eHub/2010/06")
    private String sendStreamRequestTrackingID;

    @XmlElement(name = "ActivityId", namespace = "http://schemas.microsoft.com/2004/09/ServiceModel/Diagnostics")
    private String activityId = "00000000-0000-0000-0000-000000000000";

    @XmlElement(name = "Security", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
    private Security security = new Security();

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Security {
        @XmlAttribute(name = "mustUnderstand", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
        private String mustUnderstand = "1";

        @XmlElement(name = "Timestamp", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
        private Timestamp timestamp = new Timestamp();

        @XmlElement(name = "UsernameToken", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
        private UsernameToken usernameToken ;
    }

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Timestamp {
        @XmlElement(name = "Created", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
        private String created;

        @XmlElement(name = "Expires", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
        private String expires;
    }

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UsernameToken {
        @XmlAttribute(name = "Id", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
        private String id;

        @XmlElement(name = "Username", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
        private String username;

        @XmlElement(name = "Password", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
        private Password password = new Password();
    }

    @Getter
    @Setter
    @ToString
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Password {
        @XmlAttribute(name = "Type", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0")
        private String type;

        @XmlValue
        private String value;
    }
}