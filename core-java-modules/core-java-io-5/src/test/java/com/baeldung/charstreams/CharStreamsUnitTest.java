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
    public static String TODO_LIST_PW_FILE = "pw_todos.txt";
    public static String TODO_LIST_FW_FILE = "fw_todos.txt";
    private static File pwFile; 
    private static File fwFile;
    
    @BeforeAll
    public void createDirectory() throws IOException {
        File resourcesDir
 = new File("src/test/resources");
        if (!resourcesDir.exists()){
            resourcesDir.mkdirs();
        }
    }

    @BeforeEach
    public void createFiles() throws IOException {
        try{
            pwFile = new File(resourcesDir.getAbsolutePath(), '/'+TODO_LIST_PW_FILE)
            pwFile.createNewFile();
            fwFile = new File(resourcesDir.getAbsolutePath(), '/'+TODO_LIST_FW_FILE);
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
            boolean result = CharStreams.printWriterErrorFlag(TODO_LIST_FW_FILE, TODO_2);
            assertFalse(result);
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
