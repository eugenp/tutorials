package com.baeldung.xmlhtml.helpers.jaxp;

import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import static com.baeldung.xmlhtml.Constants.*;

public class CustomHandler implements ContentHandler {

    public void startDocument() {}

    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes atts) { }

    public void endDocument() {
        System.out.println(DOCUMENT_END);
    }

    public void endElement(String uri, String localName, String qName) { }

    public void startPrefixMapping(String prefix, String uri) { }

    public void endPrefixMapping(String prefix) { }

    public void setDocumentLocator(Locator locator) { }

    public void characters(char[] ch, int start, int length) {  }

    public void ignorableWhitespace(char[] ch, int start, int length) {  }

    public void processingInstruction(String target, String data) {  }

    public void skippedEntity(String name) { }

}
