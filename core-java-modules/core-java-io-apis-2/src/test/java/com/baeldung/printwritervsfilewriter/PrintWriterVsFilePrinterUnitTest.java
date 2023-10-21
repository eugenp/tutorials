package com.baeldung.printwritervsfilewriter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;

public class PrintWriterVsFilePrinterUnitTest {

    @Test
    public void givenTextFile_whenWritingToAFileWithFileWriter_thenTextMatches() throws IOException {
        String result = "Harry Potter and Chambers of Secret";

        File fwp = new File("potter.txt");
        try (FileWriter fw = new FileWriter(fwp);) {
            fw.write("Harry Potter and Chambers of Secret");
            assertTrue(fwp.exists());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fwp));) {
            String actualData = reader.readLine();
            assertEquals(result, actualData);
        }
    }

    @Test
    public void givenTextFile_whenWritingToAFileUsingPrintfFromPrintWriter_thenTextMatches() throws IOException {
        String result = "The Dream My Father Told Me by Barack Obama";
        File fpw = new File("dream.txt");
        try (PrintWriter pw = new PrintWriter(fpw);) {
            String author = "Barack Obama";
            pw.printf("The Dream My Father Told Me by %s", author);
            pw.checkError();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fpw));) {
            String actualData = reader.readLine();
            assertEquals(result, actualData);
        }
    }

    @Test
    public void givenTextFile_whenWritingToAFileUsingPrintlnFromPrintWriter_thenTextMatches() throws IOException {
        String result = "I'm going to Alabama";
        File file = new File("alabama.txt");
        try (PrintWriter pw = new PrintWriter(file);) {
            pw.println("I'm going to Alabama");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String actualData = reader.readLine();
            assertEquals(result, actualData);
        }
    }

}
