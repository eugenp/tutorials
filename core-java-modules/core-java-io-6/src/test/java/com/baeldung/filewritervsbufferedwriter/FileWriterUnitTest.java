package com.baeldung.filewritervsbufferedwriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterUnitTest {

    private static final String FILE_PATH = "testFile.txt";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }

    @Test
    void whenWritingToFile_thenContentIsCorrect() throws IOException {
        String content = "Hello, Baeldung!";
        FileWriter writer = new FileWriter(FILE_PATH);
        writer.write(content);
        writer.close();

        StringBuilder fileContent = new StringBuilder();
        FileReader reader = new FileReader(FILE_PATH);
        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                fileContent.append((char) ch);
            }
        } finally {
            reader.close();
        }

        assertEquals(content, fileContent.toString());
    }

    @Test
    void whenFileIsWritten_thenFileExists() throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH);
        writer.write("Sample content");
        writer.close();

        File file = new File(FILE_PATH);
        assertTrue(file.exists());
    }
}