package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.jupiter.api.Test;

class whenRandomAccessFileSeek_thenMoveToIndicatedPosition {

    final char EXPECTED_CHAR = 'w';
    final String fileName = "src/test/resources/InputStreamData.txt";
    //content:
    //All work and no play makes Jack a dull boy

    @Test
    void test() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        raf.read();  // A
        raf.read();  // l
        raf.read();  // l
        raf.read();  // space

        long filePointer = raf.getFilePointer(); //at w

        raf.read();
        raf.read();
        raf.read();
        raf.read();

        raf.seek(filePointer);

        int test = raf.read();
        assertEquals(test, EXPECTED_CHAR);
    }

}
