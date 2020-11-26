package com.baeldung.file.separator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileSeparatorUnitTest {

    @Test
    public void whenUsingGetFileSeparator_thenCorrect() throws IOException {
        if (File.separator == "\\")
            assertEquals("\\", FileSeparator.getFileSeparator());
    }

    @Test
    public void whenUsingGetFileSeparatorChar_thenOutputIsAsExpected() throws IOException {
        if (File.separatorChar == '\\')
            assertEquals('\\', FileSeparator.getFileSeparatorChar());
    }

    @Test
    public void whenGetSystemProperty_thenResultIsAsExpected() throws IOException {
        if (System.getProperty("file.separator") == "\\")
            assertEquals("\\", FileSeparator.getSystemProperty());
    }

    @Test
    public void constructPath_thenResultIsAsExpected() throws IOException {
        String[] fileNames = { "src", "resources", "DataFile.txt" };
        if (File.separatorChar == '\\')
            assertEquals("src\\resources\\DataFile.txt", FileSeparator.buildFilePath(fileNames));
    }
}
