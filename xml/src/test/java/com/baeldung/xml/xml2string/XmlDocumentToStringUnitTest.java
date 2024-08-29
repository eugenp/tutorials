package com.baeldung.xml.xml2string;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;

public class XmlDocumentToStringUnitTest {

    @Test
    public void givenXMLDocument_thenConvertToStringSuccessfully() throws Exception {
        Document document = XmlDocumentToString.getDocument();

        String expectedDeclartion = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        assertEquals(expectedDeclartion + XmlDocumentToString.FRUIT_XML, XmlDocumentToString.toString(document));
    }

    @Test
    public void givenXMLDocument_thenConvertToStringWithOutputOptionsSuccessfully() throws Exception {
        Document document = XmlDocumentToString.getDocument();

        String expected = 
            "<fruit>\n"
            + "    <name>Apple</name>\n"
            + "    <color>Red</color>\n"
            + "    <weight unit=\"grams\">150</weight>\n"
            + "    <sweetness>7</sweetness>\n"
            + "</fruit>\n";
        assertEquals(expected, XmlDocumentToString.toStringWithOptions(document));
    }

}
