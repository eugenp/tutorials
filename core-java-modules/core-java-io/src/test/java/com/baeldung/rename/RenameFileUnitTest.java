package com.baeldung.rename;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenameFileUnitTest {

    private final String FILE_TO_MOVE = "src/test/resources/originalFileToMove.txt";
    private final String TARGET_FILE = "src/test/resources/targetFileToMove.txt";

    @BeforeEach
    public void createFileToMove() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        fileToMove.createNewFile();
    }

    @AfterEach
    public void cleanUpFiles() {
        File targetFile = new File(TARGET_FILE);
        targetFile.delete();
    }

    @Test
    public void givenUsingNio_whenMovingFile_thenCorrect() throws IOException {
        Path fileToMovePath = Paths.get(FILE_TO_MOVE);
        Path targetPath = Paths.get(TARGET_FILE);
        Files.move(fileToMovePath, targetPath);
    }

    @Test
    public void givenUsingFileClass_whenMovingFile_thenCorrect() throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }
    }

    @Test
    public void givenUsingGuava_whenMovingFile_thenCorrect()
            throws IOException {
        File fileToMove = new File(FILE_TO_MOVE);
        File targetFile = new File(TARGET_FILE);

        com.google.common.io.Files.move(fileToMove, targetFile);
    }

    @Test
    public void givenUsingApache_whenMovingFile_thenCorrect() throws IOException {
        FileUtils.moveFile(
                FileUtils.getFile(FILE_TO_MOVE),
                FileUtils.getFile(TARGET_FILE));
    }

}
