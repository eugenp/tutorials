package org.baeldung.di.constructor;

import java.beans.ConstructorProperties;

public class SimpleBufferedReader {
    private int bufferSize;
    private DocumentReader documentReader;

    @ConstructorProperties({ "bufferSize", "documentReader" })
    public SimpleBufferedReader(final int bufferSize, final DocumentReader documentReader) {
        this.bufferSize = bufferSize;
        this.documentReader = documentReader;
    }
}
