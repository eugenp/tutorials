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
import java.util.HashMap;
import java.util.Map;

public class JaxpTransformer {

    private Document input;
    private DocumentBuilderFactory factory;

    public JaxpTransformer(String resourcePath) throws ParserConfigurationException, IOException, SAXException {
        // 1- Build the doc from the XML file
        factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        input = factory
          .newDocumentBuilder()
          .parse(resourcePath);
    }

    public String html() throws ParserConfigurationException, TransformerException, IOException {
        Element xml = input.getDocumentElement();
        Document doc = factory
          .newDocumentBuilder()
          .newDocument();
        //Build Map
        Map<String, String> map = buildMap(xml);
        //Head
        Element html = doc.createElement("html");
        html.setAttribute("lang", "en");
        Element head = buildHead(map, doc);
        html.appendChild(head);
        //Body
        Element body = buildBody(map, doc);
        html.appendChild(body);
        doc.appendChild(html);
        return String.format("<!DOCTYPE html>%n%s", applyTransformation(doc));
    }

    private String applyTransformation(Document doc) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        try (Writer output = new StringWriter()) {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(output));
            return output.toString();
        }
    }

    private Map<String, String> buildMap(Element xml) {
        Map<String, String> map = new HashMap<>();
        map.put("heading", xml
          .getElementsByTagName("heading")
          .item(0)
          .getTextContent());
        map.put("from", String.format("from: %s", xml
          .getElementsByTagName("from")
          .item(0)
          .getTextContent()));
        map.put("content", xml
          .getElementsByTagName("content")
          .item(0)
          .getTextContent());
        return map;
    }

    private Element buildHead(Map<String, String> map, Document doc) {
        Element head = doc.createElement("head");
        Element title = doc.createElement("title");
        title.setTextContent(map.get("heading"));
        head.appendChild(title);
        return head;
    }

    private Element buildBody(Map<String, String> map, Document doc) {
        Element body = doc.createElement("body");
        Element from = doc.createElement("p");
        from.setTextContent(map.get("from"));
        Element success = doc.createElement("p");
        success.setTextContent(map.get("content"));
        body.appendChild(from);
        body.appendChild(success);
        return body;
    }

}
