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
        File pw_file = new File(todoListPwFile);
        try {
            pw_file.mkdirs();
            pw_file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            pw_file = CharStreams.writingStringToFilePrintWriter(todoListPwFile , todo.toString());
            assertTrue(pw_file.exists());
            assertEquals(todo.toString(), FileUtils.readFileToString(pw_file, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListPwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //FileWriter
        File fw_file = new File(todoListFwFile);
        try {
            fw_file.mkdirs();
            fw_file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            fw_file = CharStreams.writingStringToFileFileWriter(todoListFwFile, todo.toString());
            assertTrue(fw_file.exists());
            assertEquals(todo.toString(), FileUtils.readFileToString(fw_file, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListFwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void whenUsingFormattedData_thenPrintWriterWritesToFile() throws IOException {
        File pw = new File(todoListPwFile);
        try {
            pw.mkdirs();
            pw.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            pw = CharStreams.writingNonStringToFilePrintWriter(todoListPwFile, todo1);
            assertTrue(pw.exists());
            assertEquals(todo1.toString(), FileUtils.readFileToString(pw, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListPwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void whenUsingFormattedData_thenFileWriterWritesToFile() throws IOException {
        File fw = new File(todoListFwFile);
        try {
            fw.mkdirs();
            fw.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            CharStreams.writingNonStringToFileFileWriter(todoListFwFile, todo1);
            assertTrue(fw.exists());
            assertEquals(todo1.toString(), FileUtils.readFileToString(fw, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListFwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void whenUsingCharStreams_thenAppendsToFile() throws IOException {
        //PrintWriter
        File pw_append = new File(todoListPwFile);
        try {
            pw_append.mkdirs();
            pw_append.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            pw_append = CharStreams.appendingToFilePrintWriter(todoListPwFile, todo2);
            assertTrue(pw_append.exists());
            assertEquals(todo2.toString(), FileUtils.readFileToString(pw_append, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListPwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //FileWriter
        File fw_append = new File(todoListFwFile);
        try {
            fw_append.mkdirs();
            fw_append.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            fw_append = CharStreams.appendingToFileFileWriter(todoListFwFile, todo2);
            assertTrue(fw_append.exists());
            assertEquals(todo2.toString(), FileUtils.readFileToString(fw_append, "utf-8"));

            Files.deleteIfExists(Paths.get(todoListFwFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
