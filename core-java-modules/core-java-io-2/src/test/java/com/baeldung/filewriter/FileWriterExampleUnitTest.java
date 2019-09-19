package com.baeldung.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterExampleUnitTest {

    private static final String FILE_NAME = "src/test/resources/FileWriterTest.txt";

    @After
    public void tearDown() throws IOException {
        Files.delete(Path.of(FILE_NAME));
    }

    @Test
    public void testWriteString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write("Hello Folks!");
        }
        Assert.assertEquals("Hello Folks!", new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }

    @Test
    public void testAppendString() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write("Hello Folks!");
        }
        // using another try with resources to reopen the file in append mode
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            fileWriter.write("Hello Folks Again!");
        }

        Assert.assertEquals("Hello Folks!" + "Hello Folks Again!", new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }

    @Test
    public void testWriteCharArray() throws IOException {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write("Hello Folks!".toCharArray());
        }
        Assert.assertEquals("Hello Folks!", new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }
}
