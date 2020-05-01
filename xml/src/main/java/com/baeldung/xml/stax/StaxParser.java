package com.baeldung.xml.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.namespace.QName;

public class StaxParser {

    public static List<WebSite> parse(String path) {
        List<WebSite> websites = new ArrayList<WebSite>();
        WebSite website = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    switch (startElement.getName()
                        .getLocalPart()) {
                    case "website":
                        website = new WebSite();
                        Attribute url = startElement.getAttributeByName(new QName("url"));
                        if (url != null) {
                            website.setUrl(url.getValue());
                        }
                        break;
                    case "name":
                        nextEvent = reader.nextEvent();
                        website.setName(nextEvent.asCharacters()
                            .getData());
                        break;
                    case "category":
                        nextEvent = reader.nextEvent();
                        website.setCategory(nextEvent.asCharacters()
                            .getData());
                        break;
                    case "status":
                        nextEvent = reader.nextEvent();
                        website.setStatus(nextEvent.asCharacters()
                            .getData());
                        break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName()
                        .getLocalPart()
                        .equals("website")) {
                        websites.add(website);
                    }
                }
            }
        } catch (XMLStreamException xse) {
            System.out.println("XMLStreamException");
            xse.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            System.out.println("FileNotFoundException");
            fnfe.printStackTrace();
        }
        return websites;
    }

}
