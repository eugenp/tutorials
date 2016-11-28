package com.baeldung.java.nio2.attributes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import org.junit.Before;
import org.junit.Test;

public class BasicAttribsTest {
    private static final String HOME = System.getProperty("user.home");
    BasicFileAttributes basicAttribs;

    @Before
    public void setup() throws IOException {
        Path home = Paths.get(HOME);
        BasicFileAttributeView basicView = Files.getFileAttributeView(home, BasicFileAttributeView.class);
        basicAttribs = basicView.readAttributes();
    }

    @Test
    public void givenFileTimes_whenComparesThem_ThenCorrect() {
        FileTime created = basicAttribs.creationTime();
        FileTime modified = basicAttribs.lastModifiedTime();
        FileTime accessed = basicAttribs.lastAccessTime();

        assertTrue(0 > created.compareTo(accessed));
        assertTrue(0 < modified.compareTo(created));
        assertTrue(0 == created.compareTo(created));
    }

    @Test
    public void givenPath_whenGetsFileSize_thenCorrect() {
        long size = basicAttribs.size();
        assertTrue(size > 0);
    }

    @Test
    public void givenPath_whenChecksIfDirectory_thenCorrect() {
        boolean isDir = basicAttribs.isDirectory();
        assertTrue(isDir);
    }

    @Test
    public void givenPath_whenChecksIfFile_thenCorrect() {
        boolean isFile = basicAttribs.isRegularFile();
        assertFalse(isFile);
    }

    @Test
    public void givenPath_whenChecksIfSymLink_thenCorrect() {
        boolean isSymLink = basicAttribs.isSymbolicLink();
        assertFalse(isSymLink);
    }

    @Test
    public void givenPath_whenChecksIfOther_thenCorrect() {
        boolean isOther = basicAttribs.isOther();
        assertFalse(isOther);
    }
}
