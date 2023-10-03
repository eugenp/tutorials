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
        Files.deleteIfExists(Paths.get(TODO_LIST_PW_FILE));
        Files.createDirectories(Paths.get(TODO_LIST_FW_FILE).getParent());
        Files.deleteIfExists(Paths.get(TODO_LIST_FW_FILE));
    }

    @Test
    public void whenUsingTextData_thenPrintWriterWritesToFile() throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new File(TODO_LIST_PW_FILE), StandardCharsets.UTF_8)) {
            printWriter.print(TODO.toString());
            printWriter.close();
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString());
        }
    }

    @Test
    public void whenUsingTextData_thenFileWriterWritesToFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(TODO_LIST_FW_FILE))) {
            fileWriter.write(TODO.toString());
            fileWriter.close();
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
        }
    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new File(TODO_LIST_PW_FILE), StandardCharsets.UTF_8)) {
            printWriter.print(TODO);
            printWriter.close();
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString());
        }
    }

    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(TODO_LIST_FW_FILE))){
            fileWriter.write(TODO.toString());
            fileWriter.close();
            assertThat(new File(TODO_LIST_FW_FILE)).hasContent(TODO.toString());
        }
    }

    @Test
    public void whenWritingToExistingFile_thenPrintWriterAppendsToFile() throws IOException {
        String existingText = "existing text";
        Files.write(Paths.get(TODO_LIST_PW_FILE), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);

        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(TODO_LIST_PW_FILE, true), false, StandardCharsets.UTF_8)) {
            printWriter.append(TODO.toString());
        }
        assertThat(new File(TODO_LIST_PW_FILE)).hasContent(existingText + TODO.toString());
    }

    @Test
    public void whenWritingToExistingFile_thenFileWriterAppendsToFile() throws IOException {
        String existingText = "existing text";
        Files.write(Paths.get(TODO_LIST_FW_FILE), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);

        try (FileWriter fileWriter = new FileWriter(new File(TODO_LIST_FW_FILE), StandardCharsets.UTF_8, true)) {
            fileWriter.append(TODO.toString());
        }
        assertThat(new File(TODO_LIST_FW_FILE)).hasContent(existingText + TODO.toString());
    }

    @Test
    public void whenCheckingError_thenPrintWriterReturnsFalse() throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new File(TODO_LIST_PW_FILE), StandardCharsets.UTF_8)) {
            printWriter.print(TODO);
            boolean result = printWriter.checkError();
            printWriter.close();
            assertFalse(result);
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

    @Test
    public static void whenAutoFlushEqualTrue_thenPrintWriterFlushesAutomatically() throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(TODO_LIST_PW_FILE), true)) {
            printWriter.println(TODO);
            assertThat(new File(TODO_LIST_PW_FILE)).hasContent(TODO.toString() + System.lineSeparator());
        }
    }
}
