package org.baeldung.di.constructor;

public class SimpleMediaConverter {
    private DocumentWriter documentWriter;
    private SimpleBufferedReader mediaReader;

    public SimpleMediaConverter(final DocumentWriter documentWriter, final SimpleBufferedReader mediaReader) {

        this.documentWriter = documentWriter;
        this.mediaReader = mediaReader;
    }
}
