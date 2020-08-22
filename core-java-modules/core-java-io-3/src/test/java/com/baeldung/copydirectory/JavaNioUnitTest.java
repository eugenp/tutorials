package com.baeldung.copydirectory;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JavaNioUnitTest {

    private final String sourceDirectoryLocation = "src/test/resources/sourceDirectory2";
    private final String subDirectoryName = "/childDirectory";
    private final String fileName = "/file.txt";
    private final String destinationDirectoryLocation = "src/test/resources/destinationDirectory2";

    @BeforeEach
    public void createDirectoryWithSubdirectoryAndFile() throws IOException {
        Files.createDirectories(Paths.get(sourceDirectoryLocation));
        Files.createDirectories(Paths.get(sourceDirectoryLocation + subDirectoryName));
        Files.createFile(Paths.get(sourceDirectoryLocation + subDirectoryName + fileName));
    }

    @Test
    public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
        JavaNio.copyDirectory(sourceDirectoryLocation, destinationDirectoryLocation);

        assertTrue(new File(destinationDirectoryLocation).exists());
        assertTrue(new File(destinationDirectoryLocation + subDirectoryName).exists());
        assertTrue(new File(destinationDirectoryLocation + subDirectoryName + fileName).exists());
    }

    @Test
    public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() {
        assertThrows(IOException.class, () -> JavaNio.copyDirectory("nonExistingDirectory", destinationDirectoryLocation));
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
        if (new File(destinationDirectoryLocation).exists()) {
            Files.walk(Paths.get(destinationDirectoryLocation))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

}
