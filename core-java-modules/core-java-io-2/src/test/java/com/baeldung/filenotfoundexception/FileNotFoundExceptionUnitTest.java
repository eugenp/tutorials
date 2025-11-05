package com.baeldung.filenotfoundexception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileNotFoundExceptionUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileNotFoundExceptionUnitTest.class);

    @TempDir
    private Path tempDir;

    @Test
    void whenFileDoesNotExist_thenFileNotFoundException() {
        File notExistFile = tempDir.resolve("dummy.notExist")
            .toFile();
        assertThrows(FileNotFoundException.class, () -> {FileReader fileReader = new FileReader(notExistFile);});
    }

    @Test
    void whenFileExistButInaccessible_thenFileNotFoundException() throws IOException {
        File readOnlyFile = tempDir.resolve("dummy.ReadOnly")
            .toFile();
        // first creating a read-only file
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(readOnlyFile))) {
            printWriter.println("Hi there!");
            readOnlyFile.setReadOnly();
        }
        assertTrue(readOnlyFile.exists());
        assertFalse(readOnlyFile.canWrite());

        // now we create a FileWriter on the read-only file
        assertThrows(FileNotFoundException.class, () -> {
            FileWriter fileWriter = new FileWriter(readOnlyFile);
        });
    }

    @Test
    void whenFileNotFoundException_thenLogWarn() {
        Path notExistFile = tempDir.resolve("dummy.notExist");
        try {
            FileReader fileReader = new FileReader(notExistFile.toFile());
        } catch (FileNotFoundException ex) {
            LOG.warn("Error Occurred when reading the optional file " + notExistFile, ex);
        }
    }

    private String readConfig(Path path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return reader.readLine();
        } catch (FileNotFoundException ex) {
            throw new ConfigurationException("Reading configuration file failed", ex);
        }
    }

    @Test
    public void whenFileNotExist_thenGetBusinessException() {
        assertThrows(ConfigurationException.class, () -> readConfig(tempDir.resolve("dummy.notExist")));
    }

    public String readOrCreateFileWithContent(Path path, String defaultValue) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return reader.readLine();
        } catch (FileNotFoundException e) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(path.toFile()))) {
                writer.print(defaultValue);
                return defaultValue;
            } catch (IOException ex) {
                throw new RuntimeException("IOException when trying to create the file", ex);
            }
        } catch (IOException e) {
            throw new RuntimeException("IOException when trying to read the file", e);
        }
    }

    @Test
    public void whenFileNotExist_thenFileCreatedWithTheDefaultValue() throws IOException {
        Path path = tempDir.resolve("dummy.notExist");
        assertFalse(path.toFile()
            .exists()); // file doesn't exist

        String defaultValue = "the-default-value";
        String value = readOrCreateFileWithContent(path, defaultValue);
        assertEquals(defaultValue, value);
        assertEquals(defaultValue, Files.readString(path));
    }

}

class ConfigurationException extends RuntimeException {

    ConfigurationException(String string, FileNotFoundException ex) {
        super(string, ex);
    }
}