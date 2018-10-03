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

    private Document createSampleDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element companyElement = document.createElement("Company");
        document.appendChild(companyElement);
        Element departmentElement = document.createElement("Department");
        departmentElement.setAttribute("name", "Sales");
        companyElement.appendChild(departmentElement);
        Element employee1 = document.createElement("Employee");
        employee1.setAttribute("name", "John Smith");
        departmentElement.appendChild(employee1);
        Element employee2 = document.createElement("Employee");
        employee2.setAttribute("name", "Tim Dellor");
        departmentElement.appendChild(employee2);
        return document;
    }

    @After
    public void cleanUp() throws Exception {
        FileUtils.deleteQuietly(new File("company_simple.xml"));
        FileUtils.deleteQuietly(new File("company_prettyprinted.xml"));
    }
     
}
