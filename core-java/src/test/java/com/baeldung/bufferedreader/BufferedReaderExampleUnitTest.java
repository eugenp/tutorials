package com.baeldung.bufferedreader;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class BufferedReaderExampleUnitTest {

    public static final String FILE_PATH = "src/main/resources/input.txt";

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

    @Test
    public void givenBufferedReader_whenSkipUnderscores_thenOk() throws IOException {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new StringReader("1__2__3__4__5"))) {
            int value;
            while((value = reader.read()) != -1) {
                result.append((char) value);
                reader.skip(2L);
            }
        }

        assertEquals("12345", result.toString());
    }

    @Test
    public void givenBufferedReader_whenSkipsWhitespacesAtBeginning_thenOk() throws IOException {
        String result;

        try (BufferedReader reader = new BufferedReader(new StringReader("    Lorem ipsum dolor sit amet."))) {
            assertTrue(reader.markSupported());

            do {
                reader.mark(1);
            } while(Character.isWhitespace(reader.read()));

            reader.reset();
            result = reader.readLine();
        }

        assertEquals("Lorem ipsum dolor sit amet.", result);
    }

    @Test
    public void whenCreatesNewBufferedReader_thenOk() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH));

        assertNotNull(reader);
        assertTrue(reader.ready());

        reader.close();
    }

}
