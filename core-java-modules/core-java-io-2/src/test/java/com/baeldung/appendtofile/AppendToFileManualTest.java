package com.baeldung.appendtofile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppendToFileManualTest {

    public static final String fileName = "src/main/resources/countries.properties";

    @Before
    @After
    public void setup() throws Exception {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("UK\r\n" + "US\r\n" + "Germany\r\n");
        writer.close();
    }

    @Test
    public void whenAppendToFileUsingGuava_thenCorrect() throws IOException {
        File file = new File(fileName);
        CharSink chs = com.google.common.io.Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);
        chs.write("Spain\r\n");

        assertThat(StreamUtils.getStringFromInputStream(new FileInputStream(fileName))).isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @Test
    public void whenAppendToFileUsingFiles_thenCorrect() throws IOException {
        Files.write(Paths.get(fileName), "Spain\r\n".getBytes(), StandardOpenOption.APPEND);

        assertThat(StreamUtils.getStringFromInputStream(new FileInputStream(fileName))).isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @Test
    public void whenAppendToFileUsingFileUtils_thenCorrect() throws IOException {
        File file = new File(fileName);
        FileUtils.writeStringToFile(file, "Spain\r\n", StandardCharsets.UTF_8, true);

        assertThat(StreamUtils.getStringFromInputStream(new FileInputStream(fileName))).isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @Test
    public void whenAppendToFileUsingFileOutputStream_thenCorrect() throws Exception {
        FileOutputStream fos = new FileOutputStream(fileName, true);
        fos.write("Spain\r\n".getBytes());
        fos.close();

        assertThat(StreamUtils.getStringFromInputStream(new FileInputStream(fileName))).isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }

    @Test
    public void whenAppendToFileUsingFileWriter_thenCorrect() throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Spain");
        bw.newLine();
        bw.close();

        assertThat(StreamUtils.getStringFromInputStream(new FileInputStream(fileName))).isEqualTo("UK\r\n" + "US\r\n" + "Germany\r\n" + "Spain\r\n");
    }
}