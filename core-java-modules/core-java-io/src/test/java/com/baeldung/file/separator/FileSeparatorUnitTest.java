package com.baeldung.file.separator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class FileSeparatorUnitTest {

    @Test
    @EnabledOnOs({ OS.WINDOWS })
    @DisabledOnOs({OS.LINUX, OS.MAC})
    public void whenBuildFilePathUsingPathsClass_thenCorrect() {
        assertEquals("dir1\\dir2", FileSeparator.buildFilePathUsingPathsClass("dir1", "dir2"));

    }

    @Test
    @EnabledOnOs({ OS.WINDOWS })
    @DisabledOnOs({OS.LINUX, OS.MAC})
    public void whenBuildFilePathUsingFileClass_thenOutputIsAsExpected() {
        assertEquals("file1\\file2", FileSeparator.buildFilePathUsingFileClass("file1", "file2"));
    }

}
