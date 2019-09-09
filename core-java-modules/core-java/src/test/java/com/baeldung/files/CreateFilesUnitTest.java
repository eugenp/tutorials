package com.baeldung.files;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class CreateFilesUnitTest {
    @Test
    public void givenAnExistingDirectory_whenCreatingAFileWithAbsolutePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithAbsolutePath = new File(tempDirectory.getAbsolutePath() + "/myDirectory/testFile.txt");

        fileWithAbsolutePath.mkdirs();
        Files.touch(fileWithAbsolutePath);

        assertTrue(fileWithAbsolutePath.exists());
    }

    @Test
    public void givenAnExistingDirectory_whenCreatingANewDirectoryAndFileWithRelativePath_thenFileIsCreated() throws IOException {
        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File fileWithRelativePath = new File(tempDirectory, "myDirectory/newFile.txt");

        fileWithRelativePath.mkdirs();
        Files.touch(fileWithRelativePath);

        assertTrue(fileWithRelativePath.exists());
    }

    @Test
    public void whenCreatingAFileWithFileSeparator_thenFileIsCreated() throws IOException {
        File newFile = new File("src" + File.separator + "test" + File.separator + "resources" + File.separator + "newFile.txt");

        newFile.mkdirs();
        Files.touch(newFile);

        assertTrue(newFile.exists());
    }
}
