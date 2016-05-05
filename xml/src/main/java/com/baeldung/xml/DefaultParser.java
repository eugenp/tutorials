package com.baeldung.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DefaultParser 
{
    private File file;
     
    public DefaultParser(File file){
        this.file = file;
    }
    
    public NodeList getFirstLevelNodeList(){
        NodeList nodeList = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            
            Document xmlDocument = builder.parse(this.getFile());
            
            XPath xPath =  XPathFactory.newInstance().newXPath();
            
        } catch (SAXException | IOException | ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nodeList;
    }
    
    public NodeList getNodeListByName(String name){
        NodeList nodeList = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            
            Document xmlDocument = builder.parse(this.getFile());
            
            XPath xPath =  XPathFactory.newInstance().newXPath();
            
        } catch (SAXException | IOException | ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nodeList;
    }
    
    public Node getNodeById(String id){
        Node node = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            
            Document xmlDocument = builder.parse(this.getFile());
            
            XPath xPath =  XPathFactory.newInstance().newXPath();
            
        } catch (SAXException | IOException | ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return node;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
       
}
