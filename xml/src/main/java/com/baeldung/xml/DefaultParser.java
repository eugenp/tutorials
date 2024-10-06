package com.baeldung.xml;

import static com.baeldung.xml.SecureDocumentBuilderFactory.newSecureDocumentBuilderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DefaultParser {

    private File file;

    public DefaultParser(File file) {
        this.file = file;
    }

    public NodeList getFirstLevelNodeList() {
        NodeList nodeList = null;
        try {
            FileInputStream fileIS = new FileInputStream(this.getFile());
            DocumentBuilderFactory builderFactory = newSecureDocumentBuilderFactory();

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(fileIS);

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/tutorials/tutorial";

            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    public Node getNodeById(String id) {
        Node node = null;
        try {
            DocumentBuilderFactory builderFactory = newSecureDocumentBuilderFactory();

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(this.getFile());

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/tutorials/tutorial[@tutId=" + "'" + id + "'" + "]";

            node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return node;
    }

    public NodeList getNodeListByTitle(String name) {
        NodeList nodeList = null;
        try {
            DocumentBuilderFactory builderFactory = newSecureDocumentBuilderFactory();

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(this.getFile());

            this.clean(xmlDocument);

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "//tutorial[descendant::title[text()=" + "'" + name + "'" + "]]";

            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    public NodeList getElementsByDate(String date) {
        NodeList nodeList = null;

        try {
            DocumentBuilderFactory builderFactory = newSecureDocumentBuilderFactory();

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(this.getFile());

            this.clean(xmlDocument);

            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "//tutorial[number(translate(date, '/', '')) > " + date + "]";

            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    public NodeList getAllTutorials() {
        NodeList nodeList = null;
        try {
            DocumentBuilderFactory builderFactory = newSecureDocumentBuilderFactory();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document xmlDocument = builder.parse(this.getFile());

            this.clean(xmlDocument);

            XPath xPath = XPathFactory.newInstance().newXPath();

            xPath.setNamespaceContext(new NamespaceContext() {

                @Override
                public Iterator getPrefixes(String arg0) {
                    return null;
                }

                @Override
                public String getPrefix(String arg0) {
                    return null;
                }

                @Override
                public String getNamespaceURI(String arg0) {
                    if ("bdn".equals(arg0)) {
                        return "http://www.baeldung.com/full_archive";
                    }
                    return null;
                }
            });

            String expression = "/bdn:tutorials/bdn:tutorial";

            nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        } catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    private void clean(Node node) {

        NodeList childNodes = node.getChildNodes();

        for (int n = childNodes.getLength() - 1; n >= 0; n--) {
            Node child = childNodes.item(n);
            short nodeType = child.getNodeType();

            if (nodeType == Node.ELEMENT_NODE)
                clean(child);
            else if (nodeType == Node.TEXT_NODE) {
                String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0)
                    node.removeChild(child);
                else
                    child.setNodeValue(trimmedNodeVal);
            } else if (nodeType == Node.COMMENT_NODE)
                node.removeChild(child);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
