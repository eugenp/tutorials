package com.baeldung.xml.xml2string;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlDocumentToString {

    public static final String FRUIT_XML = "<fruit><name>Apple</name><color>Red</color><weight unit=\"grams\">150</weight><sweetness>7</sweetness></fruit>";

    public static Document getDocument() throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document = factory.newDocumentBuilder()
            .parse(new InputSource(new StringReader(FRUIT_XML)));
        return document;
    }

    public static String toString(Document document) throws TransformerException {
        StringWriter stringWriter = new StringWriter();
        getTransformer().transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public static String toStringWithOptions(Document document) throws TransformerException {
        Transformer transformer = getTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    private static Transformer getTransformer() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        return transformerFactory.newTransformer();
    }

}
