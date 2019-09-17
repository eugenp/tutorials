package com.baeldung.xmlhtml.stax;

import javax.xml.stream.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StaxTransformer {

    private Map<String, String> map;

    public StaxTransformer(String resourcePath) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        XMLStreamReader input = factory.createXMLStreamReader(new FileInputStream(resourcePath));
        map = buildMap(input);
        input.close();
    }

    public String html() throws XMLStreamException, IOException {
        try (Writer output = new StringWriter()) {
            XMLStreamWriter writer = XMLOutputFactory
              .newInstance()
              .createXMLStreamWriter(output);
            //Head
            writer.writeDTD("<!DOCTYPE html>");
            writer.writeCharacters(String.format("%n"));
            writer.writeStartElement("html");
            writer.writeAttribute("lang", "en");
            writer.writeCharacters(String.format("%n"));
            writer.writeStartElement("head");
            writer.writeCharacters(String.format("%n"));
            writer.writeDTD("<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            writer.writeCharacters(String.format("%n"));
            writer.writeStartElement("title");
            writer.writeCharacters(map.get("heading"));
            writer.writeEndElement();
            writer.writeCharacters(String.format("%n"));
            writer.writeEndElement();
            writer.writeCharacters(String.format("%n"));
            //Body
            writer.writeStartElement("body");
            writer.writeCharacters(String.format("%n"));
            writer.writeStartElement("p");
            writer.writeCharacters(map.get("from"));
            writer.writeEndElement();
            writer.writeCharacters(String.format("%n"));
            writer.writeStartElement("p");
            writer.writeCharacters(map.get("content"));
            writer.writeEndElement();
            writer.writeCharacters(String.format("%n"));
            writer.writeEndElement();
            writer.writeCharacters(String.format("%n"));
            writer.writeEndDocument();
            writer.writeCharacters(String.format("%n"));
            writer.flush();
            return output.toString();
        }
    }

    private Map<String, String> buildMap(XMLStreamReader input) throws XMLStreamException {
        Map<String, String> map = new HashMap<>();
        while (input.hasNext()) {
            input.next();
            if (input.isStartElement()) {
                if (input
                  .getLocalName()
                  .equals("heading")) {
                    map.put("heading", input.getElementText());
                }
                if (input
                  .getLocalName()
                  .equals("from")) {
                    map.put("from", String.format("from: %s", input.getElementText()));
                }
                if (input
                  .getLocalName()
                  .equals("content")) {
                    map.put("content", input.getElementText());
                }
            }
        }
        return map;
    }

    public Map<String, String> getMap() {
        return map;
    }
}
