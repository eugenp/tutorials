package com.baeldung.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4JParser {

    private File file;

    public Dom4JParser(File file) {
        this.file = file;
    }

    public Element getRootElement() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            return document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Element> getFirstElementList() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            return document.getRootElement().elements();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Node getNodeById(String id) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            List<Node> elements = document.selectNodes("//*[@tutId='" + id + "']");
            return elements.get(0);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Node getElementsListByTitle(String name) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            List<Node> elements = document.selectNodes("//tutorial[descendant::title[text()=" + "'" + name + "'" + "]]");
            return elements.get(0);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void generateModifiedDocument() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            List<Node> nodes = document.selectNodes("/tutorials/tutorial");
            for (Node node : nodes) {
                Element element = (Element) node;
                Iterator<Element> iterator = element.elementIterator("title");
                while (iterator.hasNext()) {
                    Element title = (Element) iterator.next();
                    title.setText(title.getText() + " updated");
                }
            }
            XMLWriter writer = new XMLWriter(new FileWriter(new File("src/test/resources/example_dom4j_updated.xml")));
            writer.write(document);
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void generateNewDocument() {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("XMLTutorials");
            Element tutorialElement = root.addElement("tutorial").addAttribute("tutId", "01");
            tutorialElement.addAttribute("type", "xml");

            tutorialElement.addElement("title").addText("XML with Dom4J");

            tutorialElement.addElement("description").addText("XML handling with Dom4J");

            tutorialElement.addElement("date").addText("14/06/2016");

            tutorialElement.addElement("author").addText("Dom4J tech writer");

            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileWriter(new File("src/test/resources/example_dom4j_new.xml")), format);
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
