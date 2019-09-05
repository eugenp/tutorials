package com.baeldung.xmlhtml.jaxp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JaxpTransformer {

    private Document input;
    private DocumentBuilderFactory factory;

    public JaxpTransformer(String resourcePath) throws ParserConfigurationException, IOException, SAXException {
        // 1- Build the doc from the XML file
        factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        input = factory
          .newDocumentBuilder()
          .parse(resourcePath);
    }

    public String html() throws ParserConfigurationException, TransformerException {
        Element xml = input.getDocumentElement();
        Document doc = factory
          .newDocumentBuilder()
          .newDocument();
        //Head
        Element html = doc.createElement("html");
        html.setAttribute("lang", "en");
        Element head = buildHead(xml, doc);
        html.appendChild(head);
        //Body
        Element body = buildBody(xml, doc);
        html.appendChild(body);
        doc.appendChild(html);
        Writer output = applyTransformation(doc);
        return String.format("<!DOCTYPE html>%n%s", output.toString());
    }

    private Writer applyTransformation(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Writer output = new StringWriter();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(output));
        return output;
    }

    private Element buildHead(Element xml, Document doc) {
        Element head = doc.createElement("head");
        Element title = doc.createElement("title");
        title.setTextContent(xml
          .getElementsByTagName("heading")
          .item(0)
          .getTextContent());
        head.appendChild(title);
        return head;
    }

    private Element buildBody(Element xml, Document doc) {
        Element body = doc.createElement("body");
        Element from = doc.createElement("p");
        from.setTextContent(String.format("from: %s", xml
          .getElementsByTagName("from")
          .item(0)
          .getTextContent()));
        Element success = doc.createElement("p");
        success.setTextContent(xml
          .getElementsByTagName("content")
          .item(0)
          .getTextContent());
        body.appendChild(from);
        body.appendChild(success);
        return body;
    }

}
