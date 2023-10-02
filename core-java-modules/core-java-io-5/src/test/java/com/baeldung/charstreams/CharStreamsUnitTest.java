package com.baeldung.charstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;

public class CharStreamsUnitTest {

    private static final DailyTodo TODO = new DailyTodo("Code", LocalDateTime.now(), true);
    
    public static String TODO_LIST_PW_FILE = "src/test/resources/pw_todos.txt";
    public static String TODO_LIST_FW_FILE = "src/test/resources/fw_todos.txt";
    

    @BeforeEach
    public void createFiles() throws IOException {
        Files.createDirectories(Paths.get(TODO_LIST_PW_FILE).getParent());
        Files.createDirectories(Paths.get(TODO_LIST_FW_FILE).getParent());
    }

    @Test
    public void whenUsingTextData_thenPrintWriterWritesToFile() throws IOException {
        try {
            CharStreams.writingStringToFilePrintWriter(TODO_LIST_PW_FILE , TODO.toString());
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString());
        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenUsingTextData_thenFileWriterWritesToFile() throws IOException {
        try {
            CharStreams.writingStringToFileFileWriter(TODO_LIST_FW_FILE, TODO.toString());
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        
    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        try {
            CharStreams.writingNonStringToFilePrintWriter(TODO_LIST_PW_FILE, TODO);
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        try {
            CharStreams.writingNonStringToFileFileWriter(TODO_LIST_FW_FILE, TODO);
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExistingFile_thenPrintWriterAppendsToFile() throws IOException {
        String existingText = "existing text";
        Files.write(Paths.get(TODO_LIST_PW_FILE), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        try {
            CharStreams.appendingToFilePrintWriter(TODO_LIST_PW_FILE, TODO);
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExistingFile_thenFileWriterAppendsToFile() throws IOException {
        String existingText = "some text";
        Files.write(Paths.get(TODO_LIST_FW_FILE), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        try {
            CharStreams.appendingToFileFileWriter(TODO_LIST_FW_FILE, TODO);
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenCheckingError_thenPrintWriterReturnsFalse() throws IOException {
        try {
            boolean result = CharStreams.printWriterErrorFlag(TODO_LIST_PW_FILE, TODO);
            assertFalse(result);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenHandlingError_thenFileWriterHandlesError() {
        try (FileWriter fileWriter = new FileWriter(TODO_LIST_FW_FILE, true)) {
        fileWriter.write(TODO.toString());
        } catch (IOException e) {
            fail("error not expected", e);
        }
        assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
    }
    
    @AfterEach
    public void clean() throws IOException {
        Files.deleteIfExists(Paths.get(TODO_LIST_PW_FILE));
        Files.deleteIfExists(Paths.get(TODO_LIST_FW_FILE));
    }

}
