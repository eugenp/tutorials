package com.baeldung.charstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;

public class CharStreamsUnitTest {

    private static DailyTodo TODO = new DailyTodo("Code", LocalDateTime.now(), true);
    private static DailyTodo TODO_1 = new DailyTodo("Sleep", LocalDateTime.now(), false);
    private static DailyTodo TODO_2 = new DailyTodo("Read", LocalDateTime.now(), false);
    public static String TODO_LIST_PW_FILE = "src/main/resources/pw_todos.txt";
    public static String TODO_LIST_FW_FILE = "src/main/resources/fw_todos.txt";
    private static File pwFile = new File(TODO_LIST_PW_FILE); 
    private static File fwFile =  new File(TODO_LIST_FW_FILE);

    @BeforeEach
    public void createFile() throws IOException {
        try{
            pwFile.mkdirs();
            pwFile.createNewFile();
            fwFile.mkdirs();
            fwFile.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }

    @Test
    public void whenUsingTextData_thenPrintWriterWritesToFile() throws IOException {
        try {
            pwFile = CharStreams.writingStringToFilePrintWriter(TODO_LIST_PW_FILE , TODO.toString());
            assertThat(pwFile).hasContent(TODO.toString());
        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenUsingTextData_thenFileWriterWritesToFile() throws IOException {
        try {
            fwFile = CharStreams.writingStringToFileFileWriter(TODO_LIST_FW_FILE, TODO.toString());
            assertThat(fwFile).hasContent(TODO.toString());
        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        
    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        try {
            pwFile = CharStreams.writingNonStringToFilePrintWriter(TODO_LIST_PW_FILE, TODO_1);
            assertThat(pwFile).hasContent(TODO_1.toString());
        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        try {
            fwFile = CharStreams.writingNonStringToFileFileWriter(TODO_LIST_FW_FILE, TODO_1);
            assertThat(fwFile).hasContent(TODO_1.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExixtingFile_thenPrintWriterAppendsToFile() throws IOException {
        try {
            pwFile = CharStreams.appendingToFilePrintWriter(TODO_LIST_PW_FILE, TODO_2);
            assertThat(pwFile).hasContent(TODO_2.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenWritingToExixtingFile_thenFileWriterAppendsToFile() throws IOException {
        try {
            fwFile = CharStreams.appendingToFileFileWriter(TODO_LIST_FW_FILE, TODO_2);
            assertThat(fwFile).hasContent(TODO_2.toString());
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    @Test
    public void whenCheckingError_thenPrintWriterReturnsFalse() throws IOException {
        try {
            pwFile = CharStreams.checkErrorPrintWriter(TODO_LIST_FW_FILE, TODO_2);
            assertFalse(pwFile);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }
    

    @AfterEach
    public void clean() throws IOException {
        Files.deleteIfExists(Paths.get(TODO_LIST_PW_FILE));
        Files.deleteIfExists(Paths.get(TODO_LIST_FW_FILE));
    }

}
