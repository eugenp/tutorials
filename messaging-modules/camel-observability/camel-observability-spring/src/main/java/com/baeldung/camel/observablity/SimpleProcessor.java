package com.baeldung.camel.observablity;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SimpleProcessor implements Processor {

    private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getMessage().getBody(String.class);
        String processedAt = LocalDateTime.now().toString();
        InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        Document document = factory.newDocumentBuilder().parse(stream);
        Element root = document.getDocumentElement();
        Element newElemeent = document.createElement("processed");
        newElemeent.setTextContent(processedAt);
        root.appendChild(newElemeent);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        String newBody = stringWriter.toString();
        exchange.getMessage().setBody(newBody);
    }
}
