package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ScannerNoSuchElementExceptionUnitTest {

    @Test
    void givenEmptyFile_whenUsingReadFileV1_thenThrowException() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            ScannerNoSuchElementException.readFileV1("src/test/resources/emptyFile.txt");
        });

        assertEquals("No line found", exception.getMessage());
    }

    @Test
    void givenEmptyFile_whenUsingReadFileV2_thenSuccess() throws IOException {
        String emptyLine = ScannerNoSuchElementException.readFileV2("src/test/resources/emptyFile.txt");

        assertEquals("", emptyLine);
    }

    @Test
    void givenEmptyFile_whenUsingReadFileV3_thenSuccess() throws IOException {
        String emptyLine = ScannerNoSuchElementException.readFileV3("src/test/resources/emptyFile.txt");

        assertEquals("", emptyLine);
    }

    @Test
    void givenEmptyFile_whenUsingReadFileV4_thenSuccess() throws IOException {
        String emptyLine = ScannerNoSuchElementException.readFileV4("src/test/resources/emptyFile.txt");

        assertEquals("", emptyLine);
    }

}
