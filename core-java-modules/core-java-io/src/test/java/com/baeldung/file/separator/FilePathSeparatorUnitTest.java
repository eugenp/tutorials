package com.baeldung.file.separator;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FilePathSeparatorUnitTest {

    @Test
    public void whenUsingGetFileSeparator_thenCorrect() throws IOException {
        if (File.separator == ";")
            assertEquals(";", FilePathSeparator.getFilePathSeparator());
    }

    @Test
    public void whenUsingGetFilePathSeparatorChar_thenOutputIsAsExpected() throws IOException {
        if (File.separatorChar == ';')
            assertEquals(';', FilePathSeparator.getFilePathSeparatorChar());
    }

    @Test
    public void whenGetSystemProperty_thenResultIsAsExpected() throws IOException {
        if (System.getProperty("path.separator") == ";")
            assertEquals(";", FilePathSeparator.getSystemProperty());
    }

    @Test
    public void buildFilePath_thenResultIsAsExpected() throws IOException {
        String path1 = "src" + File.separator + "resources";
        String path2 = "src" + File.separator + "main" + File.separator + "java";
        String path3 = "src" + File.separator + "test";
        String[] pathNames = { path1, path2, path3 };
        if (System.getProperty("path.separator") == ";")
            assertEquals("src\\resources;src\\main\\java;src\\test", FilePathSeparator.buildFilePath(pathNames));
    }
}
