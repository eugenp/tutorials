package com.baeldung.outputstreamtobytearray;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OutputStreamtoByteArrayUnitTest {

    @Test
    public void givenByteArrayOutputStream_whenUsingToByteArray_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!";

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.write(data.getBytes());

            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String result = new String(byteArray, StandardCharsets.UTF_8);

            Assertions.assertEquals(data, result);
        }
    }

    @Test
    public void givenFileOutputStream_whenUsingIOUtilsToReadTheFile_thenReturnByteArray() throws IOException {
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
    public void givenDrainableOutputStream_whenDraining_thenReturnByteArray() {
        DrainableOutputStream drainableOutputStream = new DrainableOutputStream();
        String data = "Welcome to Baeldung!";

        try {
            drainableOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray = drainableOutputStream.toByteArray();
        String result = new String(byteArray);

        Assertions.assertEquals(data, result);
    }

    public class DrainableOutputStream extends FilterOutputStream {
        private ByteArrayOutputStream byteArrayOutputStream;

        public DrainableOutputStream() {
            super(new ByteArrayOutputStream());
            this.byteArrayOutputStream = (ByteArrayOutputStream) out;
        }

        public byte[] toByteArray() {
            return byteArrayOutputStream.toByteArray();
        }
    }
}
