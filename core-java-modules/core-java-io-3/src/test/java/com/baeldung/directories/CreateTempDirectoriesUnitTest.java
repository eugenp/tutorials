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

        Path temporaryDirectoryPath = Files.createTempDirectory("temporary-directory-tutorial-");
        assertNotNull("Temporary directory should have been created", temporaryDirectoryPath);

        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();
        assertTrue("Newly created temporary directory is contained within the default JVM temporary directory",
                temporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(temporaryDirectoryPath);
    }

    @Test
    public void givenJavaEight_whenCreatingTemporaryDirectoryInNonDefaultLocation_thenTrue() throws IOException {

        Path nonDefaultDirectoryPath = Paths.get("some-other-location");
        Files.createDirectories(nonDefaultDirectoryPath);
        assertTrue("Non default directory must exist in order for us to create files in it", Files.exists(nonDefaultDirectoryPath));

        Path temporaryDirectoryPath = Files.createTempDirectory(nonDefaultDirectoryPath, "temporary-directory-tutorial-");
        assertNotNull("Temporary directory should have been created", temporaryDirectoryPath);

        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();
        assertTrue("Newly created temporary directory should be contained within non-default directory",
                temporaryDirectoryPath.startsWith(nonDefaultDirectoryPath));
        assertFalse("Newly created temporary directory should not be contained within the default JVM temporary directory",
                temporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(temporaryDirectoryPath);
        Files.deleteIfExists(nonDefaultDirectoryPath);
    }

    @Test
    public void givenGuava_whenCreatingTemporaryDirectory_thenTrue() throws IOException {

        File temporaryDirectoryFile = com.google.common.io.Files.createTempDir();
        assertTrue("Temporary directory should have been created",  temporaryDirectoryFile.exists() && temporaryDirectoryFile.isDirectory());

        Path newTemporaryDirectoryPath = temporaryDirectoryFile.toPath();
        Path defaultJvmTemporaryDirectory = new File(System.getProperty("java.io.tmpdir")).toPath();
        assertTrue("Newly created temporary directory is contained within the default JVM temporary directory",
                newTemporaryDirectoryPath.startsWith(defaultJvmTemporaryDirectory));

        Files.deleteIfExists(newTemporaryDirectoryPath);
    }
}
