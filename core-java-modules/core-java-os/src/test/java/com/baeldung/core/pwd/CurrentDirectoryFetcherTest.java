package com.baeldung.core.pwd;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

import org.junit.Test;

public class CurrentDirectoryFetcherTest {

    private static final String CURRENT_DIR = "core-java-os";

    @Test
    public void whenUsingSystemProperties_thenReturnCurrentDirectory() {
        String userDirectory = System.getProperty("user.dir");
        assertTrue(userDirectory.endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaNioPaths_thenReturnCurrentDirectory() {
        String userDirectory = Paths.get("")
            .toAbsolutePath()
            .toString();
        assertTrue(userDirectory.endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaNioFileSystems_thenReturnCurrentDirectory() {
        String userDirectory = FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();
        assertTrue(userDirectory.endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaIoFile_thenReturnCurrentDirectory() {
        String userDirectory = new File("").getAbsolutePath();
        assertTrue(userDirectory.endsWith(CURRENT_DIR));
    }
}