package com.baeldung.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.After;
import org.junit.Test;

import com.baeldung.util.StreamUtils;

public class FilesTest {

    public static final String fileName = "src/main/resources/countries.txt";

    @Test
    public void whenAppendToFileUsingFiles_thenCorrect() throws IOException {
        Files.write(Paths.get(fileName), "Spain\r\n".getBytes(), StandardOpenOption.APPEND);

        assertThat(StreamUtils.getStringFromInputStream(
          new FileInputStream(fileName)))
          .isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @After
    public void revertFile() throws IOException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.print("UK\r\n" + "US\r\n" + "Germany\r\n");
        writer.close();
    }
}
