package com.baeldung.xmlhtml.stax;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class StaxTransformer {

    private XMLStreamReader input;

    public StaxTransformer(String resourcePath) throws FileNotFoundException, XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        input = factory.createXMLStreamReader(new FileInputStream(resourcePath));
    }

    public String html() throws XMLStreamException {
        Map<String, String> map = buildMap();
        Writer output = new StringWriter();
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
        writer.close();
        return output.toString();
    }

    public Map<String, String> buildMap() throws XMLStreamException {
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
        input.close();
        return map;
    }
}
