package com.baeldung.directories;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class CreateTempDirectoriesUnitTest {

    @Test
    public void givenJavaEight_whenCreatingTemporaryDirectoryInDefaultLocation_thenTrue() throws IOException {

        Path temporaryDirectoryPath = Files.createTempDirectory("temp-dir-tutorial");
        assertNotNull("Temporary directory should have been created", temporaryDirectoryPath);

        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();

        assertTrue("Temporary directory is in default location",
          temporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(temporaryDirectoryPath);
    }

    @Test
    public void givenJavaEight_whenCreatingTemporaryDirectoryInNonDefaultLocation_thenTrue() throws IOException {
        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();

        Path nonDefaultDirectoryPath = Paths.get("some-other-location");
        Files.createDirectories(nonDefaultDirectoryPath);
        assertTrue("Directory must exist in order for us to create files in it",
          Files.exists(nonDefaultDirectoryPath));

        Path temporaryDirectoryPath = Files.createTempDirectory(nonDefaultDirectoryPath, "temp-dir-tutorial");

        assertNotNull("Temporary directory should have been created", temporaryDirectoryPath);
        assertTrue("Temporary directory is in expected directory",
          temporaryDirectoryPath.startsWith(nonDefaultDirectoryPath));
        assertFalse("Temporary directory is not in default location",
          temporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(temporaryDirectoryPath);
        Files.deleteIfExists(nonDefaultDirectoryPath);
    }

    @Test
    public void givenGuava_whenCreatingTemporaryDirectory_thenTrue() throws IOException {

        File temporaryDirectoryFile = com.google.common.io.Files.createTempDir();

        assertTrue("Temporary directory should have been created",
 temporaryDirectoryFile.exists() && temporaryDirectoryFile.isDirectory());

        Path newTemporaryDirectoryPath = temporaryDirectoryFile.toPath();
        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();
        assertTrue("Temporary directory is in default location",
          newTemporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(newTemporaryDirectoryPath);
    }
}
