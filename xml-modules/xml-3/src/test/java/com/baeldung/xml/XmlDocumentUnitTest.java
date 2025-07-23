package com.baeldung.xml;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class XmlDocumentUnitTest {

    @Test
    public void givenXmlString_whenConvertToDocumentViaString_thenSuccess() throws Exception {
        String xmlString = "<root><child>Example</child></root>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        assertNotNull(document);
        assertEquals("root", document.getDocumentElement().getNodeName());

        Element rootElement = document.getDocumentElement();
        var childElements = rootElement.getElementsByTagName("child");

        assertNotNull(childElements);
        assertEquals(1, childElements.getLength());
        assertEquals("Example", childElements.item(0).getTextContent());
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

    @Test
    public void givenXmlFile_whenConvertToOneLineString_thenSuccess() throws IOException {
        String filePath = "posts.xml";
        ClassLoader classLoader = getClass().getClassLoader();
        FileReader fileReader = new FileReader(classLoader
          .getResource(filePath)
          .getFile());
        StringBuilder xmlContentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContentBuilder.append(line);
            }
        }
        String xmlString = xmlContentBuilder.toString();
        // Remove tabs
        String oneLineXml = xmlString.replaceAll("\\t", "");

        // Replace multiple spaces with a single space
        oneLineXml = oneLineXml.replaceAll(" +", " ");

        // Remove spaces before/after tags (e.g., "> <" becomes "><")
        // This is important to ensure truly minimal whitespace
        oneLineXml = oneLineXml.replaceAll(">\\s+<", "><");

        // Trim leading/trailing whitespace from the entire string
        oneLineXml = oneLineXml.trim();
        String expectedXml = """
            <?xml version="1.0" encoding="UTF-8"?><posts><post postId="1"><title>Parsing XML as a String in Java</title><author>John Doe</author></post></posts>
            """;
        assertEquals(expectedXml, oneLineXml);
    }

    @Test
    public void givenInvalidXmlString_whenConvertToDocument_thenThrowException() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        String invalidXmlString = "<child>Example</child";
        assertThrows(SAXParseException.class, () -> {
            builder.parse(new InputSource(new StringReader(invalidXmlString)));
        });
    }

    @Test
    public void givenXmlString_whenConvertToDocumentViaCharStream_thenSuccess() throws Exception {
        String xmlString = "<posts><post postId='1'><title>Example Post</title><author>John Doe</author></post></posts>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource inputSource = new InputSource(new StringReader(xmlString));
        Document document = builder.parse(inputSource);

        assertNotNull(document);
        assertEquals("posts", document.getDocumentElement().getNodeName());

        Element rootElement = document.getDocumentElement();
        var childElements = rootElement.getElementsByTagName("post");
        assertNotNull(childElements);
        assertEquals(1, childElements.getLength());
    }

    @Test
    public void givenXmlString_whenConvertToDocumentViaByteArray_thenSuccess() throws Exception {
        String xmlString = "<posts><post postId='1'><title>Example Post</title><author>John Doe</author></post></posts>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        Document document = builder.parse(inputStream);

        assertNotNull(document);
        assertEquals("posts", document.getDocumentElement().getNodeName());

        Element rootElement = document.getDocumentElement();
        var childElements = rootElement.getElementsByTagName("post");
        assertNotNull(childElements);
        assertEquals(1, childElements.getLength());
    }

}
