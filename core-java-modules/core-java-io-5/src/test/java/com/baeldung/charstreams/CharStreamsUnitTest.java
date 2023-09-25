package com.baeldung.charstreams;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

public class CharStreamsUnitTest {
    public String todoListPwFile = "src/main/resources/pw_todos.txt";
    public String todoListFwFile = "src/main/resources/fr_todos.txt";

    DailyTodo todo = new DailyTodo("Code", LocalDateTime.now(), true);
    DailyTodo todo1 = new DailyTodo("Sleep", LocalDateTime.now(), false);
    DailyTodo todo2 = new DailyTodo("Read", LocalDateTime.now(), false);


    @Test
    public void whenUsingCharStreams_thenWriteToFile() throws IOException {
        //PrintWriter
        File pw_file = CharStreams.writingStringToFilePrintWriter(todoListPwFile, todo.toString());
        assertTrue(pw_file.exists());
        assertEquals(todo.toString(), FileUtils.readFileToString(pw_file, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

        //FileWriter
        File fw_file = CharStreams.writingStringToFileFileWriter(todoListFwFile, todo.toString());
        assertTrue(fw_file.exists());
        assertEquals(todo.toString(), FileUtils.readFileToString(fw_file, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        File pw = CharStreams.writingNonStringToFilePrintWriter(todoListPwFile, todo1);
        assertTrue(pw.exists());
        assertEquals(todo1.toString(), FileUtils.readFileToString(pw, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

    }
    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        File fw = CharStreams.writingNonStringToFileFileWriter(todoListFwFile, todo1);
        assertTrue(fw.exists());
        assertEquals(todo1.toString(), FileUtils.readFileToString(fw, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

    @Test
    public void whenUsingCharStreams_thenAppendsToFile() throws IOException {

        //PrintWriter
        File pw_append = CharStreams.appendingToFilePrintWriter(todoListPwFile, todo2);
        assertTrue(pw_append.exists());
        assertEquals(todo2.toString(), FileUtils.readFileToString(pw_append, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListPwFile));

        //FileWriter
        File fr_append = CharStreams.appendingToFileFileWriter(todoListFwFile, todo2);
        assertTrue(fr_append.exists());
        assertEquals(todo2.toString(), FileUtils.readFileToString(fr_append, "utf-8"));

        Files.deleteIfExists(Paths.get(todoListFwFile));

    }

}
