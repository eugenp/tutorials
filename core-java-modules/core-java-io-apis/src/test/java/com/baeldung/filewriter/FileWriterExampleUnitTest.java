package com.baeldung.filewriter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterExampleUnitTest {

    @After
    public void tearDown() throws IOException {
        Files.delete(Paths.get("src/test/resources/FileWriterTest.txt"));
    }

    @Test
    public void testWriteString() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt")) {
            fileWriter.write("Hello Folks!");
        }
        Assert.assertEquals("Hello Folks!", new String(Files.readAllBytes(Paths.get("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testAppendString() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt")) {
            fileWriter.write("Hello Folks!");
        }
        // using another try with resources to reopen the file in append mode
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt", true)) {
            fileWriter.write("Hello Folks Again!");
        }

        Assert.assertEquals("Hello Folks!" + "Hello Folks Again!", new String(Files.readAllBytes(Paths.get("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testWriteCharArray() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt")) {
            fileWriter.write("Hello Folks!".toCharArray());
        }
        Assert.assertEquals("Hello Folks!", new String(Files.readAllBytes(Paths.get("src/test/resources/FileWriterTest.txt"))));
    }
}
