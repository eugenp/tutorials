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
    private static final DailyTodo TODO_1 = new DailyTodo("Sleep", LocalDateTime.now(), false);
    private static final DailyTodo TODO_2 = new DailyTodo("Read", LocalDateTime.now(), false);
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
            pwFile = CharStreams.writingStringToFilePrintWriter(TODO_LIST_PW_FILE , TODO.toString());
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
            CharStreams.writingNonStringToFilePrintWriter(TODO_LIST_PW_FILE, TODO_1);
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO_1.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        try {
            CharStreams.writingNonStringToFileFileWriter(TODO_LIST_FW_FILE, TODO_1);
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO_1.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExixtingFile_thenPrintWriterAppendsToFile() throws IOException {
        try {
            CharStreams.appendingToFilePrintWriter(TODO_LIST_PW_FILE, TODO_2);
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO_2.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExixtingFile_thenFileWriterAppendsToFile() throws IOException {
        try {
            CharStreams.appendingToFileFileWriter(TODO_LIST_FW_FILE, TODO_2);
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO_2.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenCheckingError_thenPrintWriterReturnsFalse() throws IOException {
        try {
            boolean result = CharStreams.printWriterErrorFlag(TODO_LIST_PW_FILE, TODO_2);
            assertFalse(result);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenHandlingError_thenFileWriterHandlesError() {
        try (FileWriter fileWriter = new FileWriter(TODO_LIST_FW_FILE, true)) {
        fileWriter.write(TODO_2.toString());
        } catch (IOException e) {
            fail("error not expected", e);
        }
        assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO_2.toString());
    }
    

    @AfterEach
    public void clean() throws IOException {
        Files.deleteIfExists(Paths.get(TODO_LIST_PW_FILE));
        Files.deleteIfExists(Paths.get(TODO_LIST_FW_FILE));
    }

}
