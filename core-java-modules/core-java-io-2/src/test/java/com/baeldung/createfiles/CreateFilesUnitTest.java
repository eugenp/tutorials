package com.baeldung.createfiles;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import org.junit.BeforeClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateFilesUnitTest {

    @BeforeClass
    public static void clean() {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File file1 = new File(tempDirectory.getAbsolutePath() + "/testFile.txt");
        File file2 = new File(tempDirectory, "newFile.txt");
        File file3 = new File(tempDirectory.getAbsolutePath() + File.separator + "newFile2.txt");
        file1.delete();
        file2.delete();
        file3.delete();
    }

    @Test
    public void givenAnExistingDirectory_whenCreatingAFileWithAbsolutePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithAbsolutePath = new File(tempDirectory.getAbsolutePath() + "/testFile.txt");

        assertFalse(fileWithAbsolutePath.exists());

        Files.touch(fileWithAbsolutePath);

        assertTrue(fileWithAbsolutePath.exists());
    }

    @Test
    public void givenAnExistingDirectory_whenCreatingANewDirectoryAndFileWithRelativePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, "newFile.txt");

        assertFalse(fileWithRelativePath.exists());

        Files.touch(fileWithRelativePath);

        assertTrue(fileWithRelativePath.exists());
    }

    @Test
    public void whenCreatingAFileWithFileSeparator_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File newFile = new File(tempDirectory.getAbsolutePath() + File.separator + "newFile2.txt");

        assertFalse(newFile.exists());

        Files.touch(newFile);

        assertTrue(newFile.exists());
    }
}
