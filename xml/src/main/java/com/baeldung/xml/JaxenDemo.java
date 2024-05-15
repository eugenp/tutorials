package com.baeldung.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class JaxenDemo {

    private File file;

    public JaxenDemo(File file) {
        this.file = file;
    }

    public List getAllTutorial() {
        try {
            FileInputStream fileIS = new FileInputStream(this.getFile());
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(fileIS);

            String expression = "/tutorials/tutorial";

            XPath path = new DOMXPath(expression);
            List result = path.selectNodes(xmlDocument);
            return result;

        } catch (SAXException | IOException | ParserConfigurationException | JaxenException e) {
            e.printStackTrace();
            return null;
        }

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
