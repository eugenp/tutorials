package com.baeldung.springsoap.rawxml.saaj;

import jakarta.xml.soap.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoapXmlUtilUnitTest {

    private static final String SOAP11_NS = "http://schemas.xmlsoap.org/soap/envelope/";
    private SOAPMessage sampleMessage;
    private String xmlString;

    @BeforeEach
    void setUp() throws Exception {
        sampleMessage = createSampleSoapMessage();
        xmlString = SoapXmlUtil.soapMessageToString(sampleMessage);
    }

    @Test
    void givenValidSoapMessage_whenConvertToString_thenContainAllEssentialElements() {
        assertAll(
          () -> assertTrue(xmlString.contains(SOAP11_NS)),
          () -> assertTrue(containsElementByLocalName(xmlString, "Envelope")),
          () -> assertTrue(containsElementByLocalName(xmlString, "Body")),
          () -> assertTrue(headerIsPresentOrOmitted(xmlString)),
          () -> assertTrue(xmlString.contains("greeting")),
          () -> assertTrue(xmlString.contains("Test")),
          () -> assertTrue(xmlString.contains("http://example.com"))
        );
    }

    @Test
    void givenValidSoapMessage_whenConvertToString_thenHaveValidXmlStructure() {
        assertAll(
          () -> assertTrue(xmlString.matches("(?s).*<[^:>]+:Envelope\\b[^>]*>.*")),
          () -> assertTrue(xmlString.matches("(?s).*</[^:>]+:Envelope>.*")),
          () -> assertTrue(xmlString.matches("(?s).*<[^:>]+:Body\\b[^>]*>.*</[^:>]+:Body>.*")),
          () -> assertTrue(xmlString.matches("(?s).*<([^:>]+:)?greeting\\b[^>]*>\\s*Test\\s*</([^:>]+:)?greeting>.*"))
        );
    }

    @Test
    void givenSoapMessageWithCustomHeader_whenConvertToString_thenIncludeHeader() throws Exception {
        SOAPMessage messageWithHeader = createSoapMessageWithHeader();
        String xmlWithHeader = SoapXmlUtil.soapMessageToString(messageWithHeader);

        assertAll(
          () -> assertTrue(xmlWithHeader.contains(SOAP11_NS)),
          () -> assertTrue(containsElementByLocalName(xmlWithHeader, "Envelope")),
          () -> assertTrue(containsElementByLocalName(xmlWithHeader, "Header")),
          () -> assertTrue(containsElementByLocalName(xmlWithHeader, "Body")),
          () -> assertTrue(xmlWithHeader.contains("http://example.com/auth")),
          () -> assertTrue(xmlWithHeader.contains("token")),
          () -> assertTrue(xmlWithHeader.contains("secret123")),
          () -> assertTrue(xmlWithHeader.contains("data")),
          () -> assertTrue(xmlWithHeader.contains("http://example.com")),
          () -> assertTrue(xmlWithHeader.contains("Test"))
        );
    }

    @Test
    void givenSoapMessage_whenConvertToString_thenPreserveElementOrder() {
        int headerPos = indexOfTagStart(xmlString, "Header");
        int bodyPos = indexOfTagStart(xmlString, "Body");

        if (headerPos != -1) {
            assertTrue(headerPos < bodyPos, "Header should appear before Body");
        } else {
            assertTrue(bodyPos != -1, "Body should exist");
        }

        int bodyStart = indexOfTagStart(xmlString, "Body");
        int greetingPos = xmlString.indexOf("greeting");
        assertTrue(greetingPos > bodyStart, "greeting should be inside Body");
    }

    private SOAPMessage createSampleSoapMessage() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        SOAPBody body = message.getSOAPBody();

        SOAPElement greeting = body.addChildElement("greeting", "ns", "http://example.com");
        greeting.addTextNode("Test");

        message.saveChanges();
        return message;
    }

    private SOAPMessage createSoapMessageWithHeader() throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();

        SOAPHeader header = message.getSOAPHeader();
        SOAPHeaderElement auth = header.addHeaderElement(
          new javax.xml.namespace.QName("http://example.com/auth", "token", "auth"));
        auth.addTextNode("secret123");

        SOAPBody body = message.getSOAPBody();
        body.addChildElement("data", "ns", "http://example.com").addTextNode("Test");

        message.saveChanges();
        return message;
    }

    private static boolean containsElementByLocalName(String xml, String localName) {
        // Matches <p:LocalName ...> or <LocalName ...>
        String regex = "(?s).*<([^:>]+:)?" + localName + "\\b.*";
        return xml.matches(regex);
    }

    private static boolean headerIsPresentOrOmitted(String xml) {
        // Some providers omit the Header when empty, others render it as empty element.
        // Accept both behaviors.
        return containsElementByLocalName(xml, "Header") || !xml.matches("(?s).*<[^:>]+:Header\\b.*");
    }

    private static int indexOfTagStart(String xml, String localName) {
        // Try finding ":LocalName" (prefixed), then rewind to nearest '<'
        int colonIdx = xml.indexOf(":" + localName);
        if (colonIdx != -1) {
            int lt = xml.lastIndexOf('<', colonIdx);
            return lt;
        }
        // Fallback non-prefixed
        return xml.indexOf("<" + localName);
    }
}
