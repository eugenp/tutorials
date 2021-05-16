package com.baeldung.createfile;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateFileUnitTest {

    private final String FILE_NAME = "src/test/resources/fileToCreate.txt";

    @AfterEach
    @BeforeEach
    public void cleanUpFiles() {
        File targetFile = new File(FILE_NAME);
        targetFile.delete();
    }

    @Test
    public void givenUsingNio_whenCreatingFile_thenCorrect() throws IOException {
        Path newFilePath = Paths.get(FILE_NAME);
        Files.createFile(newFilePath);
    }

    @Test
    public void givenUsingFile_whenCreatingFile_thenCorrect() throws IOException {
        File newFile = new File(FILE_NAME);
        boolean success = newFile.createNewFile();
        assertTrue(success);
    }

    @Test
    void givenUsingFileOutputStream_whenCreatingFile_thenCorrect() throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)){
        }
    }

    @Test
    public void givenUsingGuava_whenCreatingFile_thenCorrect() throws IOException {
        com.google.common.io.Files.touch(new File(FILE_NAME));
    }

    @Test
    public void givenUsingCommonsIo_whenCreatingFile_thenCorrect() throws IOException {
        FileUtils.touch(new File(FILE_NAME));
    }

}
