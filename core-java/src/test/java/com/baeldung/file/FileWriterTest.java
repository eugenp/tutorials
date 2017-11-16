package com.baeldung.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;

import com.baeldung.util.StreamUtils;

public class FileWriterTest {
    
    public static final String fileName = "src/main/resources/countries.txt";

    @Test
    public void whenAppendToFileUsingFileWriter_thenCorrect() throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Spain");
        bw.newLine();
        bw.close();

        assertThat(
          StreamUtils.getStringFromInputStream(
          new FileInputStream(fileName)))
          .isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @After
    public void revertFile() throws IOException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("UK\r\n" + "US\r\n" + "Germany\r\n");
        writer.close();
    }
}
