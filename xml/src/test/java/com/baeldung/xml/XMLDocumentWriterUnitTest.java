package com.baeldung.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

public class XMLDocumentWriterUnitTest {

    @Test
    public void givenXMLDocumentWhenWriteIsCalledThenXMLIsWrittenToFile() throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stringInputStream = new ByteArrayInputStream("<Company><Department name=\"Sales\"><Employee name=\"John Smith\"/><Employee name=\"Tim Dellor\"/></Department></Company>".getBytes());
        Document document = documentBuilder.parse(stringInputStream);
        new XMLDocumentWriter().write(document, "company_simple.xml", false, false);
    }

    @Test
    public void givenXMLDocumentWhenWriteIsCalledWithPrettyPrintThenFormattedXMLIsWrittenToFile() throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stringInputStream = new ByteArrayInputStream("<Company><Department name=\"Sales\"><Employee name=\"John Smith\"/><Employee name=\"Tim Dellor\"/></Department></Company>".getBytes());
        Document document = documentBuilder.parse(stringInputStream);
        new XMLDocumentWriter().write(document, "company_prettyprinted.xml", false, true);
    }
}
