package com.baeldung.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;

import com.baeldung.util.StreamUtils;
import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

public class GuavaTest {

    public static final String fileName = "src/main/resources/countries.txt";

    @Test
    public void whenAppendToFileUsingFileWriter_thenCorrect() throws IOException {
        File file = new File(fileName);
        CharSink chs = Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);
        chs.write("Spain\r\n");
        
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
