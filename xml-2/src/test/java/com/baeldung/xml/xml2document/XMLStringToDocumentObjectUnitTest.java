package com.baeldung.xml2document;

import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class XMLStringToDocumentObjectUnitTest {
    @Test
    public void givenValidXMLString_whenParsing_thenDocumentIsCorrect() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xmlString = "<root><element>XML Parsing Example</element></root>";
        InputSource is = new InputSource(new StringReader(xmlString));
        Document xmlDoc = null;
        try {
            xmlDoc = builder.parse(is);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals("root", xmlDoc.getDocumentElement().getNodeName());
        assertEquals("element", xmlDoc.getDocumentElement().getElementsByTagName("element").item(0).getNodeName());
        assertEquals("XML Parsing Example", xmlDoc.getDocumentElement().getElementsByTagName("element").item(0).getTextContent());
    }
}
