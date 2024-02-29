package com.baeldung.usinggzipInputstream;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;

public class ReadingGZIPUsingGZIPInputStreamUnitTest {
    String testFilePath = Objects.requireNonNull(getClass().getClassLoader().getResource("myFile.gz")).getFile();

    @Test
    void givenGZFile_whenUsingGZIPInputStream_thenReadLines() {

        List<String> lines = new ArrayList<>();
        File file = new File(testFilePath);

        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(file));
             BufferedReader br = new BufferedReader(new InputStreamReader(gzip))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        assertEquals(3, lines.size());
        assertEquals("Line 1 content", lines.get(0));
    }
}
