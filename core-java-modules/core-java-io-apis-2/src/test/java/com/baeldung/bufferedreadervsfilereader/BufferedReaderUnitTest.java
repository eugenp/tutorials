package com.baeldung.bufferedreadervsfilereader;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderUnitTest {

    @Test
    public void whenReadingAFile_thenReadsLineByLine() {
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/sampleText1.txt"))) {
            String line;

            while((line = br.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("first line\nsecond line\nthird line\n", result.toString());
    }

}
