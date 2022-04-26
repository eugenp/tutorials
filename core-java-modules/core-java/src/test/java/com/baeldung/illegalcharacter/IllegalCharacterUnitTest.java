package com.baeldung.illegalcharacter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.junit.Test;

import com.google.gdata.util.io.base.UnicodeReader;

public class IllegalCharacterUnitTest {

    final String RESOURCE_FILE_NAME = "bom-file.txt";
    final InputStream ioStream = this.getClass()
        .getClassLoader()
        .getResourceAsStream(RESOURCE_FILE_NAME);
    final String expected = "Hello world with BOM.";

    @Test
    public void whenInputFileHasBOM_thenUseInputStream() throws IOException {
        String line;
        String actual = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(ioStream))) {
            while ((line = br.readLine()) != null) {
                actual += line;
            }
        }

        assertNotEquals(expected, actual);
    }

    @Test
    public void whenInputFileHasBOM_thenUseInputStreamWithReplace() throws IOException {
        String line;
        String actual = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ioStream)))) {
            while ((line = br.readLine()) != null) {
                actual += line.replace("\uFEFF", "");
            }
        }

        assertEquals(expected, actual);
    }

    @Test
    public void whenInputFileHasBOM_thenUseBOMInputStream() throws IOException {
        String line;
        String actual = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BOMInputStream(ioStream, false, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE)))) {
            while ((line = br.readLine()) != null) {
                actual += line;
            }
        }

        assertEquals(expected, actual);
    }

    @Test
    public void whenInputFileHasBOM_thenUseGoogleGdata() throws IOException {
        char[] actual = new char[21];

        try (Reader r = new UnicodeReader(ioStream, null)) {
            r.read(actual);
        }

        assertEquals(expected, String.valueOf(actual));
    }
}
