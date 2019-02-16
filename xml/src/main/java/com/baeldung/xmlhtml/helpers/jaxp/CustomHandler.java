package com.baeldung.xmlhtml.helpers.jaxp;

import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import static com.baeldung.xmlhtml.Constants.*;

public class CustomHandler implements ContentHandler {

     /**
     * Start document event.
     */

    public void startDocument() {
        //System.out.println(DOCUMENT_START);
    }


    /**
     * New element event.
     */

    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes atts) {
        //System.out.println(ELEMENT_START);
    }

    /**
     * End document event.
     */

    public void endDocument() {
        System.out.println(DOCUMENT_END);
    }

    /**
     * End element event.
     */

    public void endElement(String uri, String localName, String qName) {
        //System.out.println(ELEMENT_END);
    }

    /**
     * Other methods.
     */

    public void startPrefixMapping(String prefix, String uri) { }

    public void endPrefixMapping(String prefix) { }

    public void setDocumentLocator(Locator locator) { }

    public void characters(char[] ch, int start, int length) {  }

    public void ignorableWhitespace(char[] ch, int start, int length) {  }

    public void processingInstruction(String target, String data) {  }

    public void skippedEntity(String name) { }

}
