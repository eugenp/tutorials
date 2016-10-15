package org.baeldung.di.constructor;

public class SimpleDocumentParser {

    private DocumentReader documentReader;

    public SimpleDocumentParser(final DocumentReader documentReader) {
        this.documentReader = documentReader;
    }
}
