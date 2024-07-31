package com.baeldung.soapmessage;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPHeader;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SoapMessageUnitTest {

    @Test
    void givenXml_whenGetHeader_thenReturnHeader() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPHeader soapHeader = soapEnvelope.getHeader();
        assertNotNull(soapHeader);
    }

    @Test
    void givenXml_whenGetBody_thenReturnBody() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        assertNotNull(soapBody);
    }

    @Test
    void whenGetElementsByTagName_thenReturnCorrectBodyElement() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        NodeList nodes = soapBody.getElementsByTagName("be:Name");
        assertNotNull(nodes);

        Node node = nodes.item(0);
        assertEquals("Working with JUnit", node.getTextContent());
    }

    @Test
    void whenGetElementsByTagName_thenReturnCorrectHeaderElement() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPHeader soapHeader = soapEnvelope.getHeader();
        NodeList nodes = soapHeader.getElementsByTagName("be:Username");
        assertNotNull(nodes);

        Node node = nodes.item(0);
        assertNotNull(node);
        assertEquals("baeldung", node.getTextContent());
    }

    @Test
    void whenGetElementUsingIterator_thenReturnCorrectBodyElement() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        NodeList childNodes = soapBody.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if ("Name".equals(node.getLocalName())) {
                String name = node.getTextContent();
                assertEquals("Working with JUnit", name);
            }
        }
    }

    @Test
    void whenGetElementUsingXPath_thenReturnCorrectResult() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                if ("be".equals(prefix)) {
                    return "http://www.baeldung.com/soap/";
                }
                return null;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public Iterator<String> getPrefixes(String namespaceURI) {
                return null;
            }
        });

        XPathExpression expression = xpath.compile("//be:Name/text()");
        String name = (String) expression.evaluate(soapBody, XPathConstants.STRING);
        assertEquals("Working with JUnit", name);
    }

    @Test
    void whenGetElementUsingXPathAndIgnoreNamespace_thenReturnCorrectResult() throws Exception {
        SOAPEnvelope soapEnvelope = getSoapEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expression = xpath.compile("//*[local-name()='Name']/text()");

        String name = (String) expression.evaluate(soapBody, XPathConstants.STRING);
        assertEquals("Working with JUnit", name);
    }

    private SOAPEnvelope getSoapEnvelope() throws IOException, SOAPException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("soap-message.xml");
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(new MimeHeaders(), inputStream);
        SOAPPart part = soapMessage.getSOAPPart();
        return part.getEnvelope();
    }

}