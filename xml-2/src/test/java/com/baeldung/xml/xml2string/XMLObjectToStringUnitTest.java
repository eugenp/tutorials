package com.baeldung.xml2string;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Test;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

public class XMLObjectToStringUnitTest {
    private Document document;

    @Before
    public void setup() throws ParserConfigurationException {
// Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

// Create a new Document
        document = builder.newDocument();

// Create the root element
        Element rootElement = document.createElement("root");
        document.appendChild(rootElement);

// Create child elements
        Element childElement1 = document.createElement("child1");
        Element childElement2 = document.createElement("child2");

// Add text content to the child elements
        childElement1.appendChild(document.createTextNode("This is child element 1"));
        childElement2.appendChild(document.createTextNode("This is child element 2"));

// Append child elements to the root element
        rootElement.appendChild(childElement1);
        rootElement.appendChild(childElement2);
    }

    @Test
    public void givenXMLDocument_whenUsingTransformer_thenConvertXMLToString() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        String result = stringWriter.toString();

        assertTrue(result.contains("<root>"));
        assertTrue(result.contains("This is child element 1"));
        assertTrue(result.contains("This is child element 2"));
    }

    @Test
    public void givenXMLDocument_whenUsingXmlBeans_thenConvertXMLToString() {
        try {

            XmlObject xmlObject = XmlObject.Factory.parse(document);

            XmlOptions options = new XmlOptions();
            options.setSavePrettyPrint();
            options.setUseDefaultNamespace();
            options.setSaveAggressiveNamespaces();

            String xmlString = xmlObject.xmlText(options);

            xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + xmlString;

            assertTrue(xmlString.contains("<root>"));
            assertTrue(xmlString.contains("This is child element 1"));
            assertTrue(xmlString.contains("This is child element 2"));
        } catch (XmlException e) {
            e.printStackTrace();
        }
    }
}
