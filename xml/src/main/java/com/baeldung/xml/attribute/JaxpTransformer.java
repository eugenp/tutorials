package com.baeldung.xml.attribute;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class JaxpTransformer {

    public String setCustomerAttribute(String input, String oldValue, String newValue) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
        // 1- Build the doc from the XML String
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Document doc = docFactory.newDocumentBuilder()
            .parse(new InputSource(new StringReader(input)));

        // 2- Locate the node(s) with xpath
        XPath xpath = XPathFactory.newInstance()
            .newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(String.format("//*[contains(@customer, '%s')]", oldValue), doc, XPathConstants.NODESET);

        // 3- Make the change on the selected nodes
        for (int i = 0; i < nodes.getLength(); i++) {
            Element value = (Element) nodes.item(i);
            value.setAttribute("customer", newValue);
        }

        // 4- Save the result to a new XML doc
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer output = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(output));

        return output.toString();
    }
}
