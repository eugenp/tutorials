package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class whenBufferedInputStreamMarkAndReset_thenReadMarkedPosition {

    final char EXPECTED_CHAR = 'w';
    final int readLimit = 500;
    final String fileName = "src/test/resources/InputStreamTestData.txt";
    //content:
    //All work and no play makes Jack a dull boy

    @Test
    void test() throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read();    // A
        bis.read();    // l
        bis.read();    // l
        bis.read();    // space
        bis.mark(readLimit); // at w
        bis.read();
        bis.read();

        bis.reset();

        char test = (char) bis.read();
        assertEquals(test, EXPECTED_CHAR);
    }

}
