package com.baeldung.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultParserUnitTest {

    final String fileName = "src/test/resources/example_default_parser.xml";

    final String fileNameSpace = "src/test/resources/example_default_parser_namespace.xml";

    DefaultParser parser;

    @Test
    public void getFirstLevelNodeListTest() {
        parser = new DefaultParser(new File(fileName));
        NodeList list = parser.getFirstLevelNodeList();

        assertNotNull(list);
        assertTrue(list.getLength() == 4);
    }

    @Test
    public void getNodeListByTitleTest() {
        parser = new DefaultParser(new File(fileName));
        NodeList list = parser.getNodeListByTitle("XML");

        for (int i = 0; null != list && i < list.getLength(); i++) {
            Node nod = list.item(i);
            assertEquals("java", nod.getAttributes().getNamedItem("type").getTextContent());
            assertEquals("02", nod.getAttributes().getNamedItem("tutId").getTextContent());
            assertEquals("XML", nod.getFirstChild().getTextContent());
            assertEquals("title", nod.getFirstChild().getNodeName());
            assertEquals("description", nod.getChildNodes().item(1).getNodeName());
            assertEquals("Introduction to XPath", nod.getChildNodes().item(1).getTextContent());
            assertEquals("author", nod.getLastChild().getNodeName());
            assertEquals("XMLAuthor", nod.getLastChild().getTextContent());
        }
    }

    @Test
    public void getNodeByIdTest() {
        parser = new DefaultParser(new File(fileName));
        Node node = parser.getNodeById("03");

        String type = node.getAttributes().getNamedItem("type").getNodeValue();
        assertEquals("android", type);
    }

    @Test
    public void getNodeListByDateTest() {
        parser = new DefaultParser(new File(fileName));
        NodeList list = parser.getNodeListByTitle("04022016");
        for (int i = 0; null != list && i < list.getLength(); i++) {
            Node nod = list.item(i);
            assertEquals("java", nod.getAttributes().getNamedItem("type").getTextContent());
            assertEquals("04", nod.getAttributes().getNamedItem("tutId").getTextContent());
            assertEquals("Spring", nod.getFirstChild().getTextContent());
            assertEquals("title", nod.getFirstChild().getNodeName());
            assertEquals("description", nod.getChildNodes().item(1).getNodeName());
            assertEquals("Introduction to Spring", nod.getChildNodes().item(1).getTextContent());
            assertEquals("author", nod.getLastChild().getNodeName());
            assertEquals("SpringAuthor", nod.getLastChild().getTextContent());
        }
    }

    @Test
    public void getNodeListWithNamespaceTest() {
        parser = new DefaultParser(new File(fileNameSpace));
        NodeList list = parser.getAllTutorials();
        assertNotNull(list);
        assertTrue(list.getLength() == 4);
    }

}
