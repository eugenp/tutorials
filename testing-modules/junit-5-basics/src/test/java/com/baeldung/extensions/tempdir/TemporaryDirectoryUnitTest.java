package com.baeldung.extensions.tempdir;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TemporaryDirectoryUnitTest {

    @Test
    void givenTestMethodWithTempDirectoryPath_whenWriteToFile_thenContentIsCorrect(@TempDir Path tempDir) throws IOException {
        Path numbers = tempDir.resolve("numbers.txt");

        List<String> lines = Arrays.asList("1", "2", "3");
        Files.write(numbers, lines);

        assertAll(
            () -> assertTrue("File should exist", Files.exists(numbers)),
            () -> assertLinesMatch(lines, Files.readAllLines(numbers)));
    }

    @TempDir
    File anotherTempDir;

    @Test
    void givenFieldWithTempDirectoryFile_whenWriteToFile_thenContentIsCorrect() throws IOException {
        assertTrue("Should be a directory ", this.anotherTempDir.isDirectory());

        File letters = new File(anotherTempDir, "letters.txt");
        List<String> lines = Arrays.asList("x", "y", "z");

        Files.write(letters.toPath(), lines);
        
        assertAll(
            () -> assertTrue("File should exist", Files.exists(letters.toPath())),
            () -> assertLinesMatch(lines, Files.readAllLines(letters.toPath())));
    }

}
