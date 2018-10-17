package com.baeldung.bufferedreader;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BufferedReaderExampleUnitTest {

    public static final String FILE_NAME = "src/main/resources/input.txt";

    @Test
    public void givenBufferedReader_whenReadAllLines_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllLines(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllLinesWithStream_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

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
    public void givenBufferedReader_whenReadAllCharacters_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllCharacters(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllCharactersUsingArray_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllCharactersUsingArray(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadWithSkipping_thenReturnsScrambledContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readWithSkipping(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("L mottneas");
    }

    @Test
    public void givenBufferedReader_whenMarkAndReset_thenReturnsRepeatedContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.markAndReset(reader);

        assertThat(content.toString()).isNotEmpty();
        assertThat(StringUtils.countOccurrencesOf(content.toString(), "Lorem ipsum")).isEqualTo(3);
    }

    @Test
    public void whenReadFileTryWithResources_thenReturnsContent() {
        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readFileTryWithResources();

        assertThat(content.toString()).contains("Lorem ipsum");
    }

}
