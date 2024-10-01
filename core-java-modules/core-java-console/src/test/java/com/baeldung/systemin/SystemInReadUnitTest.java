package com.baeldung.systemin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class SystemInReadUnitTest {
    @Test
    void givenUserInput_whenUsingReadMultipleCharacters_thenRead() {
        System.setIn(new ByteArrayInputStream(("Hello" + System.lineSeparator() + "").getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        SystemInRead.readMultipleCharacters();

        assertEquals("Enter characters (Press 'Enter' to quit):" + System.lineSeparator() + "Hello", outputStream.toString().trim());
    }

    @Test
    void givenUserInput_whenUsingReadSingleCharacter_thenRead() {
        System.setIn(new ByteArrayInputStream("A".getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        SystemInRead.readSingleCharacter();

        assertEquals("Enter a character:" + System.lineSeparator() + "A", outputStream.toString().trim());
    }

    @Test
    void givenUserInput_whenUsingReadWithParameters_thenRead() {
        System.setIn(new ByteArrayInputStream("ABC".getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        SystemInRead.readWithParameters();

        assertEquals("Data read: ABC\n" + "Bytes Read: 3", outputStream.toString().trim());
    }
}
