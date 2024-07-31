package com.baeldung.bufferedreader;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BufferedReaderExampleUnitTest {

    private static final String FILE_PATH = "src/main/resources/input.txt";

    @Test
    public void givenBufferedReader_whenReadAllLines_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllLines(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllLinesWithStream_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllLinesWithStream(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void whenReadFile_thenReturnsContent() {
        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readFile();

        assertThat(content.toString()).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllCharsOneByOne_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllCharsOneByOne(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadMultipleChars_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readMultipleChars(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).isEqualTo("Lorem");
    }

    @Test
    public void whenReadFileTryWithResources_thenReturnsContent() {
        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readFileTryWithResources();

        assertThat(content.toString()).contains("Lorem ipsum");
    }

}
