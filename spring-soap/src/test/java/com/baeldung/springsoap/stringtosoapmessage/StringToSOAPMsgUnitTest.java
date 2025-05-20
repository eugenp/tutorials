package com.baeldung.springsoap.stringtosoapmessage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPMessage;

public class StringToSOAPMsgUnitTest {
    private final String sampleSoapXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
        "<soapenv:Header/>" +
        "<soapenv:Body>" +
        "<m:GetStockPrice xmlns:m=\"http://example.com/stock\">" +
        "<m:StockName>GOOG</m:StockName>" +
        "</m:GetStockPrice>" +
        "</soapenv:Body>" +
        "</soapenv:Envelope>";

    @Test
    public void givenSOAPString_whenUsingSAAJMessageFactory_thenReturnSOAPMessage() throws Exception {
        SOAPMessage message = StringToSOAPMsg.usingSAAJMessageFactory(sampleSoapXml);
        SOAPBody body = message.getSOAPBody();

        assertNotNull(message, "SOAPMessage should not be null");
        assertNotNull(body, "SOAP Body should not be null");
        assertTrue(body.getTextContent().contains("GOOG"), "Expected 'GOOG' not found in the SOAP body");
    }

    @Test
    public void givenSOAPString_whenUsingDOMParsing_thenReturnSOAPMessage() throws Exception {
        SOAPMessage message = StringToSOAPMsg.usingDOMParsing(sampleSoapXml);
        SOAPBody body = message.getSOAPBody();

        assertNotNull(message, "SOAPMessage should not be null");
        assertNotNull(body, "SOAP Body should not be null");
        assertTrue(body.getTextContent().contains("GOOG"), "Expected 'GOOG' not found in the SOAP body");
    }
}
