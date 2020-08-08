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

public class CoreOldUnitTest {

    private final String sourceDirectoryLocation = "src/test/resources/sourceDirectory";
    private final String subDirectoryName = "/childDirectory";
    private final String fileName = "/file.txt";
    private final String destinationDirectoryLocation = "src/test/resources/destinationDirectory";

    @BeforeEach
    public void createDirectoryWithSubdirectoryAndFile() throws IOException {
        Files.createDirectories(Paths.get(sourceDirectoryLocation));
        Files.createDirectories(Paths.get(sourceDirectoryLocation + subDirectoryName));
        Files.createFile(Paths.get(sourceDirectoryLocation + subDirectoryName + fileName));
    }

    @Test
    public void whenSourceDirectoryExists_thenDirectoryIsFullyCopied() throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        CoreOld.copyDirectoryJavaUnder7(sourceDirectory, destinationDirectory);

        assertTrue(new File(destinationDirectoryLocation).exists());
        assertTrue(new File(destinationDirectoryLocation + subDirectoryName).exists());
        assertTrue(new File(destinationDirectoryLocation + subDirectoryName + fileName).exists());
    }

    @Test
    public void whenSourceDirectoryDoesNotExist_thenExceptionIsThrown() throws IOException {
        File sourceDirectory = new File("nonExistingDirectory");
        File destinationDirectory = new File(destinationDirectoryLocation);
        assertThrows(IOException.class, () -> CoreOld.copyDirectoryJavaUnder7(sourceDirectory, destinationDirectory));
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
