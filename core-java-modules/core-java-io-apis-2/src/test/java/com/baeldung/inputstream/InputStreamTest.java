package com.baeldung.inputstream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.io.CleanupMode.ALWAYS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStreamTest {

    private Path sharedTempDir = null;
    @Test
    public void givenAString_whenWrittenToFileInputStream_thenShouldMatchWhenRead(@TempDir(cleanup = ALWAYS) Path tempDir) throws IOException {
        sharedTempDir = tempDir;
        Path sampleOut = sharedTempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList("Hello. This is just a test. Good bye.");
        Files.write(sampleOut, lines);
        File inputStreamFile = sampleOut.toFile();
        try(InputStream inputStream = new FileInputStream(inputStreamFile)){
            Assert.assertTrue(readString(inputStream).contains(lines.get(0)));
        }
    }


    private static String readString(InputStream inputStream){
        String strRet = "";
        try {
            int c;
            final StringBuilder sb = new StringBuilder();
            while(true) {

                    if (!((c = inputStream.read()) != -1))
                        break;

                    sb.append((char) c);
                }
                strRet = sb.toString();
            } catch (IOException e) {
                Assert.fail("failed in readString() " + e.getMessage());
            }
        return  strRet;
    }

    @Test
    public void givenAString_whenWrittenToByteArrayInputStream_thenShouldMatchWhenRead() throws IOException {
        String sample = "In this example, we use btye array input stream";
        byte[] byteArray = sample.getBytes(StandardCharsets.UTF_8);
        try(InputStream inputStream = new ByteArrayInputStream(byteArray)){
            Assert.assertTrue(readString(inputStream).contains(sample));
        }

    }


    @Test
    public void readWithObjectInputStream(@TempDir(cleanup = ALWAYS) Path tempDir) throws IOException, ClassNotFoundException {
        sharedTempDir = tempDir;
        Path sampleOut = sharedTempDir.resolve("sample-out.txt");
        File fileText = sampleOut.toFile();
        //Serialize a Hashmap then write it into a file.
        Map<String, String> kv = new HashMap<>();
        try(  FileOutputStream fileOutStream = new FileOutputStream( fileText)){
            try(    ObjectOutputStream objectOutStream  = new ObjectOutputStream(fileOutStream)){
                kv.put("baseURL", "baeldung.com");
                kv.put("apiKey", "this_is_a_test_key");
                objectOutStream.writeObject(kv);
            }
        }
        //Deserialize the contents of a file then transform it back into a Hashmap
        try( FileInputStream fileStream = new FileInputStream(fileText);){
            try(ObjectInputStream input = new ObjectInputStream(fileStream);){
                HashMap<String,String> inputKv = (HashMap<String, String>) input.readObject();
                Assert.assertEquals(kv.get("baseURL"), inputKv.get("baseURL"));
                Assert.assertEquals(kv.get("apiKey"), inputKv.get("apiKey"));
            }
        }
    }


}
