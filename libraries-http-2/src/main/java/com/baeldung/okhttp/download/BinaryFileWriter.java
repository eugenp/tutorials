package com.baeldung.okhttp.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BinaryFileWriter implements AutoCloseable {

    private static final int CHUNK_SIZE = 1024;
    private final OutputStream outputStream;

    public BinaryFileWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public long write(InputStream inputStream) throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(inputStream)) {
            byte[] dataBuffer = new byte[CHUNK_SIZE];
            int readBytes;
            long totalBytes = 0;
            while ((readBytes = input.read(dataBuffer)) != -1) {
                totalBytes += readBytes;
                outputStream.write(dataBuffer, 0, readBytes);
            }
            return totalBytes;
        }
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
