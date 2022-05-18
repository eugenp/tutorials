package com.baeldung.newfeatures;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class FileMismatchUnitTest {

    @Test
    public void givenIdenticalFiles_thenShouldNotFindMismatch() throws IOException {
        Path filePath1 = Files.createTempFile("file1", ".txt");
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1, "Java 12 Article");
        Files.writeString(filePath2, "Java 12 Article");
        long mismatch = Files.mismatch(filePath1, filePath2);
        assertEquals(-1, mismatch);
    }

    @Test
    public void givenDifferentFiles_thenShouldFindMismatch() throws IOException {
        Path filePath3 = Files.createTempFile("file3", ".txt");
        Path filePath4 = Files.createTempFile("file4", ".txt");
        Files.writeString(filePath3, "Java 12 Article");
        Files.writeString(filePath4, "Java 12 Tutorial");
        long mismatch = Files.mismatch(filePath3, filePath4);
        assertEquals(8, mismatch);
    }
}
