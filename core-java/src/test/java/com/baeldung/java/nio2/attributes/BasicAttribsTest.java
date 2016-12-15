package com.baeldung.java.nio2.attributes;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicAttribsTest {
    private static final String HOME = System.getProperty("user.home");
    private static BasicFileAttributes basicAttribs;

    @BeforeClass
    public static void setup() throws IOException {
        Path home = Paths.get(HOME);
        BasicFileAttributeView basicView = Files.getFileAttributeView(home, BasicFileAttributeView.class);
        basicAttribs = basicView.readAttributes();
    }

    @Test
    public void givenFileTimes_whenComparesThem_ThenCorrect() {
        FileTime created = basicAttribs.creationTime();
        FileTime modified = basicAttribs.lastModifiedTime();
        FileTime accessed = basicAttribs.lastAccessTime();

        System.out.println("Created: " + created);
        System.out.println("Modified: " + modified);
        System.out.println("Accessed: " + accessed);

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