package com.baeldung.outputstreamtobytearray;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

public class OutputStreamtoByteArrayUnitTest {

    @Test
    public void givenOutputStream_whenUsingtoByteArray_thenReturnByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String data = "Welcome to Baeldung!";

        try {
            byteArrayOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String result = new String(byteArray);

        Assert.assertEquals(data, result);
    }

    @Test
    public void givenOutputStream_whenUsingIOUtils_thenReturnByteArray() throws IOException {
        String data = "Welcome to Baeldung!";
        String filePath = "file.txt";

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(data.getBytes());
        }

        byte[] byteArray = IOUtils.toByteArray(Files.newInputStream(Paths.get(filePath)));
        String result = new String(byteArray);

        Assert.assertEquals(data, result);
    }
}
