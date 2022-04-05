package com.baeldung.core.pwd;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CurrentDirectoryFetcherUnitTest {

    private static final String CURRENT_DIR = "core-java-os";

    @Test
    public void whenUsingSystemProperties_thenReturnCurrentDirectory() {
        assertTrue(CurrentDirectoryFetcher.currentDirectoryUsingSystemProperties()
            .endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaNioPaths_thenReturnCurrentDirectory() {
        assertTrue(CurrentDirectoryFetcher.currentDirectoryUsingPaths()
            .endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaNioFileSystems_thenReturnCurrentDirectory() {
        assertTrue(CurrentDirectoryFetcher.currentDirectoryUsingFileSystems()
            .endsWith(CURRENT_DIR));
    }

    @Test
    public void whenUsingJavaIoFile_thenReturnCurrentDirectory() {
        assertTrue(CurrentDirectoryFetcher.currentDirectoryUsingFile()
            .endsWith(CURRENT_DIR));
    }
}