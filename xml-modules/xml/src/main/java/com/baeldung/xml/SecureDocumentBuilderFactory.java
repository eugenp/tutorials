package com.baeldung.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SecureDocumentBuilderFactory {
    public static DocumentBuilderFactory newSecureDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        dbf.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
        dbf.setExpandEntityReferences(false);
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        return dbf;
    }
}
