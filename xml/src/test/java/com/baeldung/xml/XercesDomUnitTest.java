package com.baeldung.xml;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XercesDomUnitTest {

    final private String FILE_NAME = "src/test/resources/example_jdom.xml";
    final private String OUTPUT_DOM = "src/test/resources/Xerces_dom.xml";

    private Document doc;
    private DocumentBuilder builder;

    @Before
    public void loadXmlFile() throws Exception {
        if (doc == null) {
            builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
            doc = builder.parse(new File(FILE_NAME));
            doc.getDocumentElement()
                .normalize();
        }
    }

    @Test
    public void whenGetElementByTag_thenSuccess() {
        NodeList nodeList = doc.getElementsByTagName("tutorial");
        Node first = nodeList.item(0);

        assertEquals(4, nodeList.getLength());
        assertEquals(Node.ELEMENT_NODE, first.getNodeType());
        assertEquals("tutorial", first.getNodeName());        
    }

    @Test
    public void whenGetFirstElementAttributes_thenSuccess() {
        Node first = doc.getElementsByTagName("tutorial")
            .item(0);
        NamedNodeMap attrList = first.getAttributes();

        assertEquals(2, attrList.getLength());
        
        assertEquals("tutId", attrList.item(0)
            .getNodeName());
        assertEquals("01", attrList.item(0)
            .getNodeValue());
        
        assertEquals("type", attrList.item(1)
            .getNodeName());
        assertEquals("java", attrList.item(1)
            .getNodeValue());
    }

    
    @Test
    public void whenTraverseChildNodes_thenSuccess() {
        Node first = doc.getElementsByTagName("tutorial")
            .item(0);
        NodeList nodeList = first.getChildNodes();
        int n = nodeList.getLength();

        Node current;
        for (int i = 0; i < n; i++) {
            current = nodeList.item(i);
            if (current.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(current.getNodeName() + ": " + current.getTextContent());
            }
        }
    }

    @Test
    public void whenModifyElementAttribute_thenModified() {
        NodeList nodeList = doc.getElementsByTagName("tutorial");
        Element first = (Element) nodeList.item(0);
        assertEquals("java", first.getAttribute("type"));

        first.setAttribute("type", "other");
        assertEquals("other", first.getAttribute("type"));
    }
    

    @Test
    public void whenCreateNewDocument_thenCreated() throws Exception {
        Document newDoc = builder.newDocument();
        Element root = newDoc.createElement("users");
        newDoc.appendChild(root);

        Element first = newDoc.createElement("user");
        root.appendChild(first);
        first.setAttribute("id", "1");

        Element email = newDoc.createElement("email");
        email.appendChild(newDoc.createTextNode("john@example.com"));
        first.appendChild(email);

        assertEquals(1, newDoc.getChildNodes()
            .getLength());
        assertEquals("users", newDoc.getChildNodes()
            .item(0)
            .getNodeName());

        printDom(newDoc);
        saveDomToFile(newDoc, OUTPUT_DOM);
    }

    private void printDom(Document document) throws Exception {
        DOMSource dom = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();

        transformer.transform(dom, new StreamResult(System.out));
    }

    private void saveDomToFile(Document document, String fileName) throws Exception {
        DOMSource dom = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();

        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(dom, result);
    }

}
