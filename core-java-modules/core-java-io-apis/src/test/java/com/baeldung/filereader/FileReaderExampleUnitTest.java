package com.baeldung.filereader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderExampleUnitTest {


    private static final String FILE_PATH = "src/test/resources/HelloWorld.txt";


    @Test
    public void givenFileReader_whenReadAllCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello, World!";
        File file = new File(FILE_PATH);
        try (FileReader fileReader = new FileReader(file)) {
            String content = FileReaderExample.readAllCharactersOneByOne(fileReader);
            Assert.assertEquals(expectedText, content);
        }
    }

    @Test
    public void givenFileReader_whenReadMultipleCharacters_thenReturnsContent() throws IOException {
        String expectedText = "Hello";
        File file = new File(FILE_PATH);
        try (FileReader fileReader = new FileReader(file)) {
            String content = FileReaderExample.readMultipleCharacters(fileReader, 5);
            Assert.assertEquals(expectedText, content);
        }
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = FileReaderExample.readFile(FILE_PATH);
        Assert.assertEquals(expectedText, content);
    }

    @Test
    public void whenReadFileUsingTryWithResources_thenReturnsContent() {
        String expectedText = "Hello, World!";
        String content = FileReaderExample.readFileUsingTryWithResources(FILE_PATH);
        Assert.assertEquals(expectedText, content);
    }

}
