package com.baeldung.filereadmethods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilUnitTest {

    @Test
    void testReadFileFromResource() {
        String result = FileIOUtil.readFileFromResource("/test.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }

    @Test
    void testReadFileFromFileSystem() {
        String result = FileIOUtil.readFileFromFileSystem("src/test/resources/test.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }

    @Test
    void testReadFileFromResourceExternalFile() {
        String result = FileIOUtil.readFileFromResource("../external.txt");
        assertNull(result);
    }

    @Test
    void testReadFileFromFileSystemExternalFile() {
        String result = FileIOUtil.readFileFromFileSystem("../external.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }

}