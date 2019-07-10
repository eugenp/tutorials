package com.baeldung.xmlhtml.helpers.stax;

import com.baeldung.xmlhtml.pojo.stax.Body;
import com.baeldung.xmlhtml.pojo.stax.CustomElement;
import com.baeldung.xmlhtml.pojo.stax.NestedElement;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import static com.baeldung.xmlhtml.Constants.*;

public class STAXHelper {

    private static XMLStreamReader reader() {
        XMLStreamReader xmlStreamReader = null;
        try {
            xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(XML_FILE_IN));
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
        return xmlStreamReader;
    }

    private static XMLStreamWriter writer() {
        XMLStreamWriter xmlStreamWriter = null;
        try {
            xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileWriter(STAX_FILE_OUT));
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
        return xmlStreamWriter;
    }

    public static Body read() {
        Body body = new Body();
        try {
            XMLStreamReader xmlStreamReader = reader();

            CustomElement customElement = new CustomElement();
            NestedElement nestedElement = new NestedElement();
            CustomElement child = new CustomElement();

            while (xmlStreamReader.hasNext()) {
                xmlStreamReader.next();
                if (xmlStreamReader.isStartElement()) {
                    System.out.println(xmlStreamReader.getLocalName());
                    if (xmlStreamReader.getLocalName().equals("descendantOne")) {
                        customElement.setValue(xmlStreamReader.getElementText());
                    }
                    if (xmlStreamReader.getLocalName().equals("descendantThree")) {
                        child.setValue(xmlStreamReader.getElementText());
                    }
                }
            }

            nestedElement.setCustomElement(child);
            body.setCustomElement(customElement);
            body.setNestedElement(nestedElement);

            xmlStreamReader.close();

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
        return body;
    }

    public static void write(Body body) {
        try {

            XMLStreamWriter xmlStreamWriter = writer();
            xmlStreamWriter.writeStartElement("html");
            xmlStreamWriter.writeCharacters(BREAK + TAB);
            xmlStreamWriter.writeStartElement("head");
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB);
            xmlStreamWriter.writeStartElement("meta");
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK + TAB);
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK + TAB);
            xmlStreamWriter.writeStartElement("body");
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB);
            xmlStreamWriter.writeStartElement("p");
            xmlStreamWriter.writeCharacters("descendantOne: " + body.getCustomElement().getValue());
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB);
            xmlStreamWriter.writeStartElement("div");
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB + TAB);
            xmlStreamWriter.writeStartElement("p");
            xmlStreamWriter.writeCharacters(BREAK);
            xmlStreamWriter.writeCharacters(TAB + TAB + TAB + TAB + "descendantThree: " + body.getNestedElement().getCustomElement().getValue());
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB + TAB);
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK + TAB + TAB);
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK + TAB);
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeCharacters(BREAK);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();
            xmlStreamWriter.close();

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }

    }
}
