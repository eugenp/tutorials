package com.baeldung.fileseparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FilePathSeparatorUnitTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenCheckPathSeparator_thenResultIsAsExpectedOnWindows() throws IOException {
        assertEquals(";", File.pathSeparator);
        assertEquals(';', File.pathSeparatorChar);
    }
    
    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenCheckPathSeparator_thenResultIsAsExpected() throws IOException {
        assertEquals(":", File.pathSeparator);
        assertEquals(':', File.pathSeparatorChar);
    }
    
    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildPathUsingString_thenResultIsAsExpectedOnWindows() throws IOException {
        String[] pathNames = { "path1", "path2", "path3" };
        String path = String.join(File.pathSeparator, pathNames);
        assertEquals("path1;path2;path3",path);
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildPathUsingString_thenResultIsAsExpected() throws IOException {
        String[] pathNames = { "path1", "path2", "path3" };
        String path = String.join(File.pathSeparator, pathNames);
        assertEquals("path1:path2:path3", path);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenbuildPathUsingStringJoiner_thenResultIsAsExpectedOnWindows() throws IOException {
        assertEquals("path1;path2", buildPathUsingStringJoiner("path1", "path2"));
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenbuildPathUsingStringJoiner_thenResultIsAsExpected() throws IOException {
        assertEquals("path1:path2", buildPathUsingStringJoiner("path1", "path2"));
    }
    
    private String buildPathUsingStringJoiner(String path1, String path2) {
        StringJoiner joiner = new StringJoiner(File.pathSeparator);
        joiner.add(path1);
        joiner.add(path2);
        return joiner.toString();
    }
}