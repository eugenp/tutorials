package com.baeldung.springsoap.rawxml.saaj;

import jakarta.xml.soap.SOAPMessage;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class SoapXmlUtil {

    public static String soapMessageToString(SOAPMessage message) {
        try {
            if (message == null) {
                throw new IllegalArgumentException("SOAPMessage cannot be null");
            }
            message.saveChanges();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            message.writeTo(out);

            return out.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert SOAPMessage to String", e);
        }
    }
}