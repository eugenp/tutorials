package com.baeldung.rename;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RenameFileUnitTest {

    private final String FILE_TO_MOVE = "originalFileToMove.txt";
    private final String TARGET_FILE = "targetFileToMove.txt";

    @BeforeEach
    public void createFileToMove(@TempDir Path tempDir) throws IOException {
        File fileToMove = tempDir.resolve(FILE_TO_MOVE).toFile();
        fileToMove.createNewFile();
    }

    @Test
    public void givenUsingNio_whenMovingFile_thenCorrect(@TempDir Path tempDir) throws IOException {
        Path fileToMovePath = tempDir.resolve(FILE_TO_MOVE);
        Path targetPath = tempDir.resolve(TARGET_FILE);

        Files.move(fileToMovePath, targetPath);
    }

    @Test
    public void givenUsingFileClass_whenMovingFile_thenCorrect(@TempDir Path tempDir) throws IOException {
        File fileToMove = tempDir.resolve(FILE_TO_MOVE).toFile();
        File targetFile = tempDir.resolve(TARGET_FILE).toFile();

        boolean isMoved = fileToMove.renameTo(targetFile);
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }
    }

    @Test
    public void givenUsingGuava_whenMovingFile_thenCorrect(@TempDir Path tempDir) throws IOException {
        File fileToMove = tempDir.resolve(FILE_TO_MOVE).toFile();
        File targetFile = tempDir.resolve(TARGET_FILE).toFile();

        com.google.common.io.Files.move(fileToMove, targetFile);
    }

    @Test
    public void givenUsingApache_whenMovingFile_thenCorrect(@TempDir Path tempDir) throws IOException {
        FileUtils.moveFile(
                FileUtils.getFile(tempDir.resolve(FILE_TO_MOVE).toString()),
                FileUtils.getFile(tempDir.resolve(TARGET_FILE).toString()));
    }

}
