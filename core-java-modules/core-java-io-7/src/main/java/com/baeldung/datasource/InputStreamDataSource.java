package com.baeldung.datasource;

import javax.activation.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDataSource implements DataSource {

    private final InputStream inputStream;
    private final String contentType;

    public InputStreamDataSource(InputStream inputStream, String contentType) {
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Output not supported");
    }

    @Override
    public String getContentType() {
        return contentType != null ? contentType : "*/*";
    }

    @Override
    public String getName() {
        return "InputStreamDataSource";
    }
}