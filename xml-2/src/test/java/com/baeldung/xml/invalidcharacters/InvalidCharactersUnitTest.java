package com.baeldung.xml.invalidcharacters;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidCharactersUnitTest {

    @Test
    void givenXml_whenReservedCharacters_thenThrowException() {
        String invalidXmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?><root><name>John & Doe</name></root>";
        assertThrowsExactly(SAXParseException.class, () -> parseXmlString(invalidXmlString));
    }

    @Test
    void givenXml_whenReservedCharactersEscaped_thenSuccess() {
        String validXmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?><root><name>John &amp; Doe</name></root>";

        assertDoesNotThrow(() -> {
            Document document = parseXmlString(validXmlString);

            assertNotNull(document);
            assertEquals("John & Doe", document.getElementsByTagName("name").item(0).getTextContent());
        });
    }
    
    @Test
    void givenXml_whenUsingCdataForReservedCharacters_thenSuccess() {
        String validXmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?><root><name><![CDATA[John & Doe]]></name></root>";

        assertDoesNotThrow(() -> {
            Document document = parseXmlString(validXmlString);

            assertNotNull(document);
            assertEquals("John & Doe", document.getElementsByTagName("name").item(0).getTextContent());
        });
    }    

    @Test
    void givenXml_whenUnicodeCharacters_thenThrowException() {
        String invalidXmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?><root><name>John \u001E Doe</name></root>";
        assertThrowsExactly(SAXParseException.class, () -> parseXmlString(invalidXmlString));
    }

    @Test
    void givenXml_whenUnicodeCharactersEscaped_thenSuccess() {
        String validXmlString = "<?xml version=\"1.1\" encoding=\"UTF-8\"?><root><name>John &#x1E; Doe</name></root>";
        assertDoesNotThrow(() -> {
            Document document = parseXmlString(validXmlString);

            assertNotNull(document);
            assertEquals("John \u001E Doe", document.getElementsByTagName("name").item(0).getTextContent());
        });
    }

    private Document parseXmlString(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(xmlString));
        return builder.parse(inputSource);
    }
}
