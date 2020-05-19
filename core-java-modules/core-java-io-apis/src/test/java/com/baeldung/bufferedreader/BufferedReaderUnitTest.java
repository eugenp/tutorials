package com.baeldung.bufferedreader;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class BufferedReaderUnitTest {

    private static final String FILE_PATH = "src/main/resources/input.txt";

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
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            assertNotNull(reader);
            assertTrue(reader.ready());
        }
    }

}
