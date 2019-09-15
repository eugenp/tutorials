package com.baeldung.filewriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterExampleUnitTest {

    private static final String FILE_NAME = "src/test/resources/FileWriterTest.txt";
    private static final String STRING_TO_WRITE = "Hello Folks!";
    private static final String STRING_TO_APPEND = "Hello Folks Again!";
    private static final char[] CHAR_ARRAY_TO_WRITE = STRING_TO_WRITE.toCharArray();

    private FileWriterExample fileWriterExample = new FileWriterExample();

    @After
    public void tearDown() throws IOException {
        Files.delete(Path.of(FILE_NAME));
    }

    @Test
    public void testWriteString() throws IOException {
        fileWriterExample.writeString(FILE_NAME, STRING_TO_WRITE);
        Assert.assertEquals(STRING_TO_WRITE, new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }

    @Test
    public void testAppendString() throws IOException {
        fileWriterExample.writeString(FILE_NAME, STRING_TO_WRITE);
        fileWriterExample.appendString(FILE_NAME, STRING_TO_APPEND);
        Assert.assertEquals(STRING_TO_WRITE + STRING_TO_APPEND, new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }

    @Test
    public void testWriteCharArray() throws IOException {
        fileWriterExample.writeCharArray(FILE_NAME, CHAR_ARRAY_TO_WRITE);
        Assert.assertEquals(STRING_TO_WRITE, new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }
    
    @Test
    public void testWriteWithBufferedWriter() throws IOException{
        final List<String> stringsToWrite = new ArrayList<>();
        for(int i=0 ; i < 10000;i++) {
            stringsToWrite.add("Random string "+i);
        }
        fileWriterExample.writeWithBufferedWriter(FILE_NAME, stringsToWrite);
        Assert.assertNotNull(new String(Files.readAllBytes(Path.of(FILE_NAME))));
    }
}
