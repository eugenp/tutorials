package com.baeldung.outputstreamtobytearray;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputStreamToByteArrayUnitTest {

    @Test
    public void givenFileOutputStream_whenUsingFileUtilsToReadTheFile_thenReturnByteArray(@TempDir Path tempDir) throws IOException {
        String data = "Welcome to Baeldung!";
        String fileName = "file.txt";
        Path filePath = tempDir.resolve(fileName);

        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            outputStream.write(data.getBytes(StandardCharsets.UTF_8));
        }

        byte[] writtenData = FileUtils.readFileToByteArray(filePath.toFile());
        String result = new String(writtenData, StandardCharsets.UTF_8);
        assertEquals(data, result);
    }


    @Test
    public void givenSystemOut_whenUsingDrainableOutputStream_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!\n";

        DrainableOutputStream drainableOutputStream = new DrainableOutputStream(System.out);
        try (drainableOutputStream) {
            drainableOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
        }

        byte[] writtenData = drainableOutputStream.toByteArray();
        assertEquals(data, new String(writtenData, StandardCharsets.UTF_8));
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
