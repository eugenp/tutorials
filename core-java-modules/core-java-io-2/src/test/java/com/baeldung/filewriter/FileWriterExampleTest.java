package com.baeldung.filewriter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Test;

public class FileWriterExampleTest {

    @After
    public void tearDown() throws IOException {
        Files.delete(Path.of("src/test/resources/FileWriterTest.txt"));
    }

    @Test
    public void testFileWriter_Constructor_With_FileName() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt")) {
            fileWriter.write("Hello Folks!");
        }

        assertEquals("Hello Folks!", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_FileName_And_Charset() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt", Charset.forName("US-ASCII"))) {
            fileWriter.write("Hello Folks! Let us learn the Façade design pattern");
        }

        System.out.println(new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
        assertEquals("Hello Folks! Let us learn the Fa?ade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_FileName_And_AppendFlag() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt")) {
            fileWriter.write("Hello Folks!");
        }

        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt", true)) {
            fileWriter.write("Let us learn the Façade design pattern");
        }

        assertEquals("Hello Folks!Let us learn the Façade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_FileName_And_Charset_And_AppendFlag() throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt", Charset.forName("US-ASCII"))) {
            fileWriter.write("Hello Folks!");
        }

        try (FileWriter fileWriter = new FileWriter("src/test/resources/FileWriterTest.txt", Charset.forName("US-ASCII"), true)) {
            fileWriter.write("Let us learn the Façade design pattern");
        }

        assertEquals("Hello Folks!Let us learn the Fa?ade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_File() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"))) {
            fileWriter.write("Hello Folks!");
        }

        assertEquals("Hello Folks!", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_File_And_Charset() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"), Charset.forName("US-ASCII"))) {
            fileWriter.write("Hello Folks!Let us learn the Façade design pattern");
        }

        assertEquals("Hello Folks!Let us learn the Fa?ade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_File_And_AppendFlag() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"))) {
            fileWriter.write("Hello Folks!");
        }

        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"), true)) {
            fileWriter.write("Let us learn the Façade design pattern");
        }

        assertEquals("Hello Folks!Let us learn the Façade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_File_And_Charset_And_AppendFlag() throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"), Charset.forName("US-ASCII"))) {
            fileWriter.write("Hello Folks!");
        }

        try (FileWriter fileWriter = new FileWriter(new File("src/test/resources/FileWriterTest.txt"), Charset.forName("US-ASCII"), true)) {
            fileWriter.write("Let us learn the Façade design pattern");
        }

        assertEquals("Hello Folks!Let us learn the Fa?ade design pattern", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

    @Test
    public void testFileWriter_Constructor_With_FileDescriptor() throws FileNotFoundException, IOException {
        try (FileOutputStream fos = new FileOutputStream(new File("src/test/resources/FileWriterTest.txt")); FileWriter fileWriter = new FileWriter(fos.getFD())) {
            fileWriter.write("Hello Folks!");
        }

        assertEquals("Hello Folks!", new String(Files.readAllBytes(Path.of("src/test/resources/FileWriterTest.txt"))));
    }

}
