package org.baeldung.di.constructor;

import org.springframework.beans.factory.annotation.Qualifier;

public class SimpleDocumentParserAnnotation {
    private DocumentReader documentReader;

    public SimpleDocumentParserAnnotation(@Qualifier("documentReader") final DocumentReader documentReader) {
        this.documentReader = documentReader;
    }
}
