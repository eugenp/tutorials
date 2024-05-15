package com.baeldung.extensions.tempdir;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
class SharedTemporaryDirectoryUnitTest {

    @TempDir
    static Path sharedTempDir;
    
    @Test
    @Order(1)
    void givenFieldWithSharedTempDirectoryPath_whenWriteToFile_thenContentIsCorrect() throws IOException {
        Path numbers = sharedTempDir.resolve("numbers.txt");

        List<String> lines = Arrays.asList("1", "2", "3");
        Files.write(numbers, lines);

        assertAll(
            () -> assertTrue("File should exist", Files.exists(numbers)),
            () -> assertLinesMatch(lines, Files.readAllLines(numbers)));
        
        Files.createTempDirectory("bpb");
    }

    @Test
    @Order(2)
    void givenAlreadyWrittenToSharedFile_whenCheckContents_thenContentIsCorrect() throws IOException {
        Path numbers = sharedTempDir.resolve("numbers.txt");

        assertLinesMatch(Arrays.asList("1", "2", "3"), Files.readAllLines(numbers));
    }

}
