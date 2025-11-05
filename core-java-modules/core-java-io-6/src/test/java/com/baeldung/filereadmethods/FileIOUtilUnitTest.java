package com.baeldung.filereadmethods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilUnitTest {

    @Test
    void givenFileUnderResources_whenReadFileFromResource_thenSuccess() {
        String result = FileIOUtil.readFileFromResource("/test.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }


    @Test
    void givenFileUnderResources_whenReadFileFromFileSystem_thenSuccess() {
        String result = FileIOUtil.readFileFromFileSystem("src/test/resources/test.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }
    @Test
    void givenFileOutsideResources_whenReadFileFromFileSystem_thenSuccess() {
        String result = FileIOUtil.readFileFromFileSystem("../external.txt");
        assertNotNull(result);
        assertEquals(result, "Hello!\n" +
                "Welcome to the world of Java NIO.");
    }

    @Test
    void givenFileOutsideResources_whenReadFileFromResource_thenNull() {
        String result = FileIOUtil.readFileFromResource("../external.txt");
        assertNull(result);
    }

}