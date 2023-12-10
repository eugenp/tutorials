package com.baeldung.outputstreamtobytearray;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class OutputStreamtoByteArrayUnitTest {

    @Test
    public void givenFileOutputStream_whenUsingFileUtilsToReadTheFile_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!";
        String filePath = "file.txt";

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(data.getBytes());
        }

        byte[] byteArray = FileUtils.readFileToByteArray(new File(filePath));
        String result = new String(byteArray);

        Assertions.assertEquals(data, result);
    }


    @Test
    public void givenFileOutputStream_whenUsingDrainableOutputStream_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!";
        String filePath = "file.txt";
        DrainableOutputStream drainableOutputStream = new DrainableOutputStream(new FileOutputStream(filePath));
        drainableOutputStream.write(data.getBytes());
        byte[] intercepted = drainableOutputStream.toByteArray();
        String result = new String(intercepted);
        Assertions.assertEquals(data, result);
    }

    public class DrainableOutputStream extends FilterOutputStream {
        private final ByteArrayOutputStream buffer;

        public DrainableOutputStream(OutputStream out) {
            super(out);
            this.buffer = new ByteArrayOutputStream();
        }

        @Override
        public void write(byte b[]) throws IOException {
            this.buffer.write(b);
            super.write(b);
        }

        public byte[] toByteArray() {
            return this.buffer.toByteArray();
        }
    }
}
