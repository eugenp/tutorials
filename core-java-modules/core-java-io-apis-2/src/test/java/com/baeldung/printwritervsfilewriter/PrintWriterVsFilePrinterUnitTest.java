package com.baeldung.printwritervsfilewriter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PrintWriterVsFilePrinterUnitTest {

    @Test
    public void whenWritingToTextFileUsingFileWriter_thenTextMatches() throws IOException {
        String result = "Harry Potter and the Chamber of Secrets";

        File file = new File("potter.txt");
        try (FileWriter fw = new FileWriter(file);) {
            fw.write("Harry Potter and the Chamber of Secrets");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String actualData = reader.readLine();
            assertEquals(result, actualData);
        }
    }

    @Test
    public void whenWritingToTextFileUsingPrintWriterPrintf_thenTextMatches() throws IOException {
        String result = "Dreams from My Father by Barack Obama";
        File file = new File("dream.txt");
        try (PrintWriter pw = new PrintWriter(file);) {
            String author = "Barack Obama";
            pw.printf("Dreams from My Father by %s", author);
            assertTrue(!pw.checkError());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String actualData = reader.readLine();
            assertEquals(result, actualData);
        }
    }

    @Test
    public void whenWritingToTextFileUsingPrintWriterPrintln_thenTextMatches() throws IOException {
        String result = "I'm going to Alabama\nAlabama is a state in the US\n";
        try (PrintWriter pw = new PrintWriter("alabama.txt");) {
            pw.println("I'm going to Alabama");
            pw.println("Alabama is a state in the US");
        }
        Path path = Paths.get("alabama.txt");
        String actualData = new String(Files.readAllBytes(path));
        assertEquals(result, actualData);
    }

}
