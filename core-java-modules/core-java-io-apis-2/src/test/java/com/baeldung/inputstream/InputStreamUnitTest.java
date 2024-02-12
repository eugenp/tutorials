package com.baeldung.inputstream;

import static org.junit.jupiter.api.io.CleanupMode.ALWAYS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStreamUnitTest {
    @Test
    public void givenAString_whenWrittenToFileInputStream_thenShouldMatchWhenRead(@TempDir Path tempDir) throws IOException {
        Path sampleOut = tempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList("Hello. This is just a test. Good bye.");
        Files.write(sampleOut, lines);
        File sampleOutFile = sampleOut.toFile();
        try (InputStream inputStream = new FileInputStream(sampleOutFile)) {
            Assert.assertTrue(readString(inputStream).contains(lines.get(0)));
        }
    }

    @Test
    public void givenAString_whenWrittenToFileInputStreamWithStringConstructor_thenShouldMatchWhenRead(@TempDir Path tempDir) throws IOException {
        Path sampleOut = tempDir.resolve("sample-out.txt");
        String expectedText = "Hello. Hi.";
        List<String> lines = Arrays.asList(expectedText);
        Files.write(sampleOut, lines);
        String fileAbsolutePath = sampleOut.toFile()
            .getAbsolutePath();
        try (FileInputStream fis = new FileInputStream(fileAbsolutePath)) {
            int content;
            int availabeBytes = fis.available();
            Assert.assertTrue(availabeBytes > 0);
            StringBuilder actualReadText = new StringBuilder();
            while ((content = fis.read()) != -1) {
                actualReadText.append((char) content);
            }
            Assert.assertTrue(actualReadText.toString()
                .contains(expectedText));
        }
    }

    @Test
    public void givenAString_whenWrittenToByteArrayInputStream_thenShouldMatchWhenRead() throws IOException {
        byte[] byteArray = { 104, 101, 108, 108, 111 };
        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray)) {
            String expected = readString(bais);
            Assert.assertEquals("hello", expected);
        }
    }

    private static String readString(InputStream inputStream) throws IOException {
        int c;
        StringBuilder sb = new StringBuilder();
        while ((c = inputStream.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    @Test
    public void givenKeyValuePairs_whenWrittenInATextFile_thenSuccessWhenParsedWithObjectInputStream(
          @TempDir(cleanup = ALWAYS) Path tempDir) throws IOException, ClassNotFoundException {
        Path sampleOut = tempDir.resolve("sample-out.txt");
        File fileText = sampleOut.toFile();
        //Serialize a Hashmap then write it into a file.
        Map<String, String> kv = new HashMap<>();
        try (FileOutputStream fileOutStream = new FileOutputStream(fileText)) {
            try (ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream)) {
                kv.put("baseURL", "baeldung.com");
                kv.put("apiKey", "this_is_a_test_key");
                objectOutStream.writeObject(kv);
            }
        }
        //Deserialize the contents of a file then transform it back into a Hashmap
        try (FileInputStream fileStream = new FileInputStream(fileText)) {
            try (ObjectInputStream input = new ObjectInputStream(fileStream)) {
                HashMap<String, String> inputKv = (HashMap<String, String>) input.readObject();
                Assert.assertEquals(kv.get("baseURL"), inputKv.get("baseURL"));
                Assert.assertEquals(kv.get("apiKey"), inputKv.get("apiKey"));
            }
        }
    }
}
