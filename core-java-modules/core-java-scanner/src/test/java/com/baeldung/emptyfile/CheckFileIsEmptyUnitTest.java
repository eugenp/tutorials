package com.baeldung.emptyfile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CheckFileIsEmptyUnitTest {
    @Test
    void whenTheFileIsEmpty_thenFileLengthIsZero(@TempDir Path tempDir) throws IOException {
        File emptyFile = tempDir.resolve("an-empty-file.txt")
          .toFile();
        emptyFile.createNewFile();
        assertTrue(emptyFile.exists());
        assertEquals(0, emptyFile.length());
    }

    @Test
    void whenFileDoesNotExist_thenFileLengthIsZero(@TempDir Path tempDir) {
        File aNewFile = tempDir.resolve("a-new-file.txt")
          .toFile();
        assertFalse(aNewFile.exists());
        assertEquals(0, aNewFile.length());
    }

    boolean isFileEmpty(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Cannot check the file length. The file is not found: " + file.getAbsolutePath());
        }
        return file.length() == 0;
    }

    @Test
    void whenTheFileDoesNotExist_thenIsFilesEmptyThrowsException(@TempDir Path tempDir) {
        File aNewFile = tempDir.resolve("a-new-file.txt")
          .toFile();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> isFileEmpty(aNewFile));
        assertEquals(ex.getMessage(), "Cannot check the file length. The file is not found: " + aNewFile.getAbsolutePath());
    }

    @Test
    void whenTheFileIsEmpty_thenIsFilesEmptyReturnsTrue(@TempDir Path tempDir) throws IOException {
        File emptyFile = tempDir.resolve("an-empty-file.txt")
          .toFile();
        emptyFile.createNewFile();
        assertTrue(isFileEmpty(emptyFile));

    }

    @Test
    void whenTheFileIsEmpty_thenFilesSizeReturnsTrue(@TempDir Path tempDir) throws IOException {
        Path emptyFilePath = tempDir.resolve("an-empty-file.txt");
        Files.createFile(emptyFilePath);
        assertEquals(0, Files.size(emptyFilePath));
    }

    @Test
    void whenTheFileDoesNotExist_thenFilesSizeThrowsException(@TempDir Path tempDir) {
        Path aNewFilePath = tempDir.resolve("a-new-file.txt");
        assertThrows(NoSuchFileException.class, () -> Files.size(aNewFilePath));
    }
}