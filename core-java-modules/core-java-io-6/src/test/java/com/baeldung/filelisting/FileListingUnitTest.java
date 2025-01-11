package com.baeldung.filelisting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileListingUnitTest {

    @TempDir
    Path tempDir;

    File tempFile;

    /**
     * Create a temporary directory and files for testing
     * @throws IOException if an I/O error occurs. Shouldn't happen in this case
     */
    @BeforeEach
    public void setup() throws IOException {
        Path directoryPath = tempDir.resolve("src/main/resources/filelisting/");
        Files.createDirectories(directoryPath);
        tempFile = directoryPath.toFile();
        Files.createFile(directoryPath.resolve("file1.txt"));
        Files.createFile(directoryPath.resolve("file2.txt"));
        Path subDirectoryPath = Files.createDirectory(directoryPath.resolve("directory1"));
        Files.createFile(subDirectoryPath.resolve("file3.txt"));
        Files.createFile(subDirectoryPath.resolve("file4.txt"));
    }

    /**
     * Testing Apache Commons IO library
     */
    @Test
    public void whenListFilesCommonsIO_thenIterateFiles() {
        FileListing.listFilesCommonsIO(tempFile);
        assertTrue(tempFile.exists());
    }

    /**
     * Testing Java NIO class 'Files'
     */
    @Test
    public void whenListFilesJavaNIO_thenFilesWalk() {
        FileListing.listFilesJavaNIO(tempDir);
        assertTrue(Files.exists(tempDir.resolve("src/main/resources/filelisting/")));
    }

    /**
     * Testing Java IO class 'File'
     */
    @Test
    public void whenListFilesJavaIO_thenListFiles() {
        FileListing.listFilesJavaIO(tempFile);
        assertTrue(tempFile.exists());
    }
}