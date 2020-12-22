package com.baeldung.fileseparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FileSeparatorUnitTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildFilePathUsingPathsClass_thenCorrectOnWindows() {
        assertEquals("dir1\\dir2", Paths.get("dir1", "dir2").toString());
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildFilePathUsingPathsClass_thenCorrect() {
        assertEquals("dir1/dir2", Paths.get("dir1", "dir2").toString());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildFilePathUsingFileClass_thenOutputIsAsExpectedOnWindows() {
        assertEquals("file1\\file2", new File("file1", "file2").toString());
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildFilePathUsingFileClass_thenOutputIsAsExpected() {
        assertEquals("file1/file2", new File("file1", "file2").toString());
    }
}
