package com.baeldung.charstreams;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

public class CharStreamsUnitTest {
    public String todoListPwFile = "src/main/resources/pw_todos.txt";
    public String todoListFwFile = "src/main/resources/fw_todos.txt";

    DailyTodo todo = new DailyTodo("Code", LocalDateTime.now(), true);
    DailyTodo todo1 = new DailyTodo("Sleep", LocalDateTime.now(), false);
    DailyTodo todo2 = new DailyTodo("Read", LocalDateTime.now(), false);


    @Test
    public void whenUsingCharStreams_thenWriteToFile() throws IOException {
        //PrintWriter
        CharStreams.writingStringToFilePrintWriter(todoListPwFile , todo.toString());
        File pw_file = new File(todoListPwFile);
        assertTrue(pw_file.exists());
        assertEquals(todo.toString(), FileUtils.readFileToString(pw_file, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

        //FileWriter
        CharStreams.writingStringToFileFileWriter(todoListFwFile, todo.toString());
        File fw_file = new File(todoListFwFile);
        assertTrue(fw_file.exists());
        assertEquals(todo.toString(), FileUtils.readFileToString(fw_file, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        CharStreams.writingNonStringToFilePrintWriter(todoListPwFile, todo1);
        File pw = new File(todoListPwFile);
        assertTrue(pw.exists());
        assertEquals(todo1.toString(), FileUtils.readFileToString(pw, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

    }
    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        CharStreams.writingNonStringToFileFileWriter(todoListFwFile, todo1);
        File fw = new File(todoListFwFile);
        assertTrue(fw.exists());
        assertEquals(todo1.toString(), FileUtils.readFileToString(fw, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

    @Test
    public void whenUsingCharStreams_thenAppendsToFile() throws IOException {

        //PrintWriter
        CharStreams.appendingToFilePrintWriter(todoListPwFile, todo2);
        File pw_append = new File(todoListPwFile);
        assertTrue(pw_append.exists());
        assertEquals(todo2.toString(), FileUtils.readFileToString(pw_append, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

        //FileWriter
        CharStreams.appendingToFileFileWriter(todoListFwFile, todo2);
        File fw_append = new File(todoListFwFile);
        assertTrue(fw_append.exists());
        assertEquals(todo2.toString(), FileUtils.readFileToString(fw_append, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

}
