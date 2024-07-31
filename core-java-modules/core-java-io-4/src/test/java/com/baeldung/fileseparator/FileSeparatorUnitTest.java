package com.baeldung.fileseparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FileSeparatorUnitTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenCheckFileSeparator_thenCorrectOnWindows() {
        assertEquals("\\", File.separator);
        assertEquals('\\', File.separatorChar);
        
        String fileSeparator = FileSystems.getDefault().getSeparator();
        assertEquals("\\",fileSeparator);
    }
    
    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenCheckFileSeparator_thenCorrect() {
        assertEquals("/", File.separator);
        assertEquals('/', File.separatorChar);
        
        String fileSeparator = FileSystems.getDefault().getSeparator();
        assertEquals("/",fileSeparator);
    }
    
    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildFilePathUsingPathsClass_thenCorrectOnWindows() {
        Path path = Paths.get("dir1", "dir2");
        assertEquals("dir1\\dir2", path.toString());
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildFilePathUsingPathsClass_thenCorrect() {
        Path path = Paths.get("dir1", "dir2");
        assertEquals("dir1/dir2", path.toString());
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void whenBuildFilePathUsingFileClass_thenOutputIsAsExpectedOnWindows() {
        File file = new File("file1", "file2");
        assertEquals("file1\\file2", file.toString());
    }

    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    public void whenBuildFilePathUsingFileClass_thenOutputIsAsExpected() {
        File file = new File("file1", "file2");
        assertEquals("file1/file2", file.toString());
    }
}