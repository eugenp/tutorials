package com.baeldung.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.baeldung.xml.binding.Tutorial;

public class StaxParser {

    private File file;

    public StaxParser(File file) {
        this.file = file;
    }

    public List<Tutorial> getAllTutorial() {
        boolean bTitle = false;
        boolean bDescription = false;
        boolean bDate = false;
        boolean bAuthor = false;
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(this.getFile()));
            Tutorial current = null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("tutorial")) {
                        current = new Tutorial();
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute currentAt = attributes.next();
                            if (currentAt.getName().toString().equalsIgnoreCase("tutId")) {
                                current.setTutId(currentAt.getValue());
                            } else if (currentAt.getName().toString().equalsIgnoreCase("type")) {
                                current.setType(currentAt.getValue());
                            }
                        }
                    } else if (qName.equalsIgnoreCase("title")) {
                        bTitle = true;
                    } else if (qName.equalsIgnoreCase("description")) {
                        bDescription = true;
                    } else if (qName.equalsIgnoreCase("date")) {
                        bDate = true;
                    } else if (qName.equalsIgnoreCase("author")) {
                        bAuthor = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    Characters characters = event.asCharacters();
                    if (bTitle) {
                        if (current != null) {
                            current.setTitle(characters.getData());
                        }
                        bTitle = false;
                    }
                    if (bDescription) {
                        if (current != null) {
                            current.setDescription(characters.getData());
                        }
                        bDescription = false;
                    }
                    if (bDate) {
                        if (current != null) {
                            current.setDate(characters.getData());
                        }
                        bDate = false;
                    }
                    if (bAuthor) {
                        if (current != null) {
                            current.setAuthor(characters.getData());
                        }
                        bAuthor = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("tutorial")) {
                        if (current != null) {
                            tutorials.add(current);
                        }
                    }
                    break;
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return tutorials;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
