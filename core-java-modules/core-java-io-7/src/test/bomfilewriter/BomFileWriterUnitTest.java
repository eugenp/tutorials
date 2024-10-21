package com.baeldung.bomfilewriter;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BomFileWriterUnitTest {
    private static final String FILE_PATH_OUTPUT_STREAM = "output_with_bom.txt";
    private static final String TEST_CONTENT = "This is the content of the file";
    private static final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final String FILE_PATH_BUFFERED_WRITER = "output_with_bom_buffered.txt";
    private static final String FILE_PATH_COMMONS_IO = "output_with_bom_commons_io.txt";
    private static final String FILE_PATH_PRINT_WRITER = "output_with_bom_print_writer.txt";

    @Test
    public void givenText_whenAddingBomWithFileOutputStream_thenBOMAdded() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH_OUTPUT_STREAM)) {
            fos.write(UTF8_BOM);
            fos.write(TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
        }

        String result = Files.readString(Path.of(FILE_PATH_OUTPUT_STREAM), StandardCharsets.UTF_8);
        assertTrue(result.startsWith("\uFEFF"));
        assertTrue(result.contains(TEST_CONTENT));
    }

    @Test
    public void givenText_whenAddingBomWithBufferedWriter_thenBOMAdded() throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(FILE_PATH_BUFFERED_WRITER), StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {

            writer.write("\uFEFF");
            writer.write(TEST_CONTENT);
        }

        String result = Files.readString(Path.of(FILE_PATH_BUFFERED_WRITER), StandardCharsets.UTF_8);
        assertTrue(result.startsWith("\uFEFF"));
        assertTrue(result.contains(TEST_CONTENT));
    }

    @Test
    public void givenText_whenUsingCommonsIO_thenBOMAdded() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH_COMMONS_IO)) {
            fos.write(UTF8_BOM);
            FileUtils.writeStringToFile(new File(FILE_PATH_COMMONS_IO), TEST_CONTENT, StandardCharsets.UTF_8, true);
        }

        String result = Files.readString(Path.of(FILE_PATH_COMMONS_IO), StandardCharsets.UTF_8);
        assertTrue(result.startsWith("\uFEFF"));
        assertTrue(result.contains(TEST_CONTENT));
    }

    @Test
    public void givenText_whenUsingPrintWriter_thenBOMAdded() throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_PATH_PRINT_WRITER), StandardCharsets.UTF_8))) {
            writer.write("\uFEFF");
            writer.println(TEST_CONTENT);
        }

        String result = Files.readString(Path.of(FILE_PATH_PRINT_WRITER), StandardCharsets.UTF_8);
        assertTrue(result.startsWith("\uFEFF"));
        assertTrue(result.contains(TEST_CONTENT));
    }
}