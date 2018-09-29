package com.baeldung.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLDocumentWriter {
    
    public void write(Document document, String fileName) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            return;
        }
                
        try {
            DOMSource source = new DOMSource(document);
            FileWriter writer = new FileWriter(new File(fileName));
            StreamResult result = new StreamResult(writer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
