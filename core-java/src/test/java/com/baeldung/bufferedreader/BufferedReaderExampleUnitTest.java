package com.baeldung.bufferedreader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BufferedReaderExampleUnitTest {

    public static final String FILE_NAME = "src/main/resources/input.txt";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restore() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void givenBufferedReader_whenReadAllLines_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllLines(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void givenBufferedReader_whenReadAllLines2_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllLines2(reader);

        assertThat(content).isNotEmpty();
        assertThat(content).contains("Lorem ipsum");
    }

    @Test
    public void whenReadFile_thenOutputsContent() {
        BufferedReaderExample bre = new BufferedReaderExample();
        bre.readFile();

        assertThat(outContent.toString()).contains("Lorem ipsum");
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
    public void givenBufferedReader_whenReadAllCharacter2_thenReturnsContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        String content = bre.readAllCharacters2(reader);

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
    public void givenBufferedReader_whenMarkAndReset_thenOutputsRepeatedContent() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));

        BufferedReaderExample bre = new BufferedReaderExample();
        bre.markAndReset(reader);

        assertThat(outContent.toString()).isNotEmpty();
        assertThat(StringUtils.countOccurrencesOf(outContent.toString(), "Lorem ipsum")).isEqualTo(3);
    }

    @Test
    public void whenReadFileTryWithResources_thenOutputsContent() {
        BufferedReaderExample bre = new BufferedReaderExample();
        bre.readFileTryWithResources();

        assertThat(outContent.toString()).contains("Lorem ipsum");
    }

}
