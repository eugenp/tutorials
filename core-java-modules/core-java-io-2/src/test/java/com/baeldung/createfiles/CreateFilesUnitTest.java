package com.baeldung.createfiles;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateFilesUnitTest {
    @Test
    public void givenAnExistingDirectory_whenCreatingAFileWithAbsolutePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithAbsolutePath = new File(tempDirectory.getAbsolutePath() + "/testFile1.txt");

        try {
            assertFalse(fileWithAbsolutePath.exists());

            Files.touch(fileWithAbsolutePath);

            assertTrue(fileWithAbsolutePath.exists());
        }
        finally {
            fileWithAbsolutePath.delete();
        }
    }

    @Test
    public void givenAnExistingDirectory_whenCreatingANewDirectoryAndFileWithRelativePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, "testFile2.txt");

        try {
            assertFalse(fileWithRelativePath.exists());

            Files.touch(fileWithRelativePath);

            assertTrue(fileWithRelativePath.exists());
        }
        finally {
            fileWithRelativePath.delete();
        }
    }

    @Test
    public void whenCreatingAFileWithFileSeparator_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File newFile = new File(tempDirectory.getAbsolutePath() + File.separator + "testFile3.txt");

        try {
            assertFalse(newFile.exists());

            Files.touch(newFile);

            assertTrue(newFile.exists());
        }
        finally {
            newFile.delete();
        }            
    }
}
