package com.baeldung.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import com.baeldung.util.StreamUtils;

public class FileUtilsTest {

    public static final String fileName = "src/main/resources/countries.txt";

    @Test
    public void whenAppendToFileUsingFiles_thenCorrect() throws IOException {
        File file = new File(fileName);
        FileUtils.writeStringToFile(file, "Spain\r\n", StandardCharsets.UTF_8, true);
        
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
