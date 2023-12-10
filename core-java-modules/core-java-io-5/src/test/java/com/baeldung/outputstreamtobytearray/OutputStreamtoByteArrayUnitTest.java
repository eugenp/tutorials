package com.baeldung.outputstreamtobytearray;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class OutputStreamtoByteArrayUnitTest {

    @Test
    public void givenFileOutputStream_whenUsingFileUtilsToReadTheFile_thenReturnByteArray(@TempDir Path tempDir) throws IOException {
        String data = "Welcome to Baeldung!";
        String fileName = "file.txt";
        Path filePath = tempDir.resolve(fileName);

        try (DrainableOutputStream drainableOutputStream = new DrainableOutputStream(new FileOutputStream(filePath.toFile()))) {
            drainableOutputStream.write(data.getBytes());
        }

        byte[] intercepted = Files.readAllBytes(filePath);
        String result = new String(intercepted);
        assertEquals(data, result);
    }


    @Test
    public void givenFileOutputStream_whenUsingDrainableOutputStream_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!";
        String filePath = "file.txt";

        DrainableOutputStream drainableOutputStream = null;

        try {
            drainableOutputStream = new DrainableOutputStream(new FileOutputStream(filePath));
            drainableOutputStream.write(data.getBytes());
        } finally {
            if (drainableOutputStream != null) {
                drainableOutputStream.close();
            }
        }

        byte[] intercepted = Files.readAllBytes(Paths.get(filePath));
        String result = new String(intercepted);
        assertEquals(data, result);
    }

    public class DrainableOutputStream extends FilterOutputStream {
        private final ByteArrayOutputStream buffer;

        public DrainableOutputStream(OutputStream out) {
            super(out);
            this.buffer = new ByteArrayOutputStream();
        }

        @Override
        public void write(byte b[]) throws IOException {
            buffer.write(b);
            super.write(b);
        }

        public byte[] toByteArray() {
            return buffer.toByteArray();
        }
    }
}
