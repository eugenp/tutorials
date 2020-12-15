package com.baeldung.file.separator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FilePathSeparatorUnitTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildPathUsingString_thenResultIsAsExpectedOnWindows() throws IOException {
        String[] pathNames = { "path1", "path2", "path3" };
        assertEquals("path1;path2;path3", FilePathSeparator.buildPathUsingString(pathNames));
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildPathUsingString_thenResultIsAsExpected() throws IOException {
        String[] pathNames = { "path1", "path2", "path3" };
        assertEquals("path1:path2:path3", FilePathSeparator.buildPathUsingString(pathNames));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenbuildPathUsingStringJoiner_thenResultIsAsExpectedOnWindows() throws IOException {
        assertEquals("path1;path2", FilePathSeparator.buildPathUsingStringJoiner("path1", "path2"));
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenbuildPathUsingStringJoiner_thenResultIsAsExpected() throws IOException {
        assertEquals("path1:path2", FilePathSeparator.buildPathUsingStringJoiner("path1", "path2"));
    }
}
