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
    void testReadFileFromFileSystemOutsideResources() {
        String result = FileIOUtil.readFileFromFileSystem("./test.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }

    @Test
    void ReadFileFromResourceOutsideResources() {
        String result = FileIOUtil.readFileFromResource("./test.txt");
        assertNull(result);
    }

}