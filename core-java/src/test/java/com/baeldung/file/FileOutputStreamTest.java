package com.baeldung.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;

import com.baeldung.util.StreamUtils;

public class FileOutputStreamTest {

    public static final String fileName = "src/main/resources/countries.txt";

    @Test
    public void whenAppendToFileUsingFileOutputStream_thenCorrect() throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName, true);
        fos.write("Spain\r\n".getBytes());
        fos.close();

        assertThat(StreamUtils.getStringFromInputStream(
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
