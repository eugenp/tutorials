package com.baeldung.stringtosoapmessage;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

public class StringToSOAPMsg {

    public static SOAPMessage usingSAAJMessageFactory(String soapXml) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(soapXml.getBytes(StandardCharsets.UTF_8));
        MessageFactory factory = MessageFactory.newInstance();
        return factory.createMessage(null, input);
    }

    public static SOAPMessage usingDOMParsing(String soapXml) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(soapXml.getBytes(StandardCharsets.UTF_8)));
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        SOAPPart part = message.getSOAPPart();
        part.setContent(new DOMSource(doc.getDocumentElement()));
        message.saveChanges();
        return message;
    }
}
