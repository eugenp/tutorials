package com.baeldung.xml;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class XmlDocumentUnitTest {

    @Test
    public void givenXmlString_whenConvertToDocument_thenSuccess() throws Exception {
        String xmlString = "<root><child>Example</child></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        assertNotNull(document);
        assertEquals("root", document.getDocumentElement().getNodeName());

        Element rootElement = document.getDocumentElement();
        assertNotNull(rootElement.getElementsByTagName("child"));
        assertEquals(1, rootElement.getElementsByTagName("child").getLength());
        assertEquals("Example", rootElement.getElementsByTagName("child").item(0).getTextContent());
    }

    @Test
    public void givenDocument_whenInsertNode_thenNodeAdded() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document existingDocument = builder.newDocument();
        Element rootElement = existingDocument.createElement("existingRoot");
        existingDocument.appendChild(rootElement);

        String xmlString = "<child>Example</child>";
        Document newDocument = builder.parse(new InputSource(new StringReader(xmlString)));

        Element newNode = (Element) existingDocument.importNode(newDocument.getDocumentElement(), true);
        existingDocument.getDocumentElement().appendChild(newNode);

        assertNotNull(existingDocument);
        assertEquals(1, existingDocument.getDocumentElement().getChildNodes().getLength());
        assertEquals("child", existingDocument.getDocumentElement().getChildNodes().item(0).getNodeName());
    }
}

