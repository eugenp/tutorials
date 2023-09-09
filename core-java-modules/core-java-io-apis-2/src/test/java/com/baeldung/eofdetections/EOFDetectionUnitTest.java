package com.baeldung.eofdetections;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EOFDetectionUnitTest {

    static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
    String pathToFile = "sample.txt"; // init sample file path
    EOFDetection eofDetection = new EOFDetection();

    @Test
    @Order(1)
    public void testWithFileInputStream() {
        try {
            assertEquals(LOREM_IPSUM, eofDetection.readWithFIleInputStream(pathToFile));
        } catch (IOException e) {
            fail(e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testReadFileWithBufferedReader() {
        try {
            assertEquals(LOREM_IPSUM, eofDetection.readWithBufferedReader(pathToFile));
        } catch (IOException e) {
            fail(e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    public void testReadFileWithScanner() {
        try {
            assertEquals(LOREM_IPSUM, eofDetection.readWithScanner(pathToFile));
        } catch (IOException e) {
            fail(e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void testReadFileWithFileChannelAndByteBuffer() {
        try {
            assertEquals(LOREM_IPSUM, eofDetection.readFileWithFileChannelAndByteBuffer(pathToFile));
        } catch (IOException e) {
            fail(e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    @Test
    @Order(0)
    public void prepareFileForTest() {
        File file = new File(pathToFile);

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(LOREM_IPSUM);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
