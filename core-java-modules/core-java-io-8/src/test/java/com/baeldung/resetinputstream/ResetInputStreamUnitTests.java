package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.jupiter.api.Test;

public class ResetInputStreamUnitTests {

    final int readLimit = 500;
    final String fileName = "src/test/resources/InputStreamData.txt";
    
    @Test
    void givenByteArrayInputStream_whenMarkSupported_thenTrue() {
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        boolean isMarkSupported = bis.markSupported();
        assertEquals(isMarkSupported, true);
    }
    
    @Test
    void givenByteArrayInputStream_whenMarkAndReset_thenReadMarkedPosition() {
        final int EXPECTED_NUMBER = 3;
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        int number = bis.read();    //get 1
        number = bis.read();        //get 2
        bis.mark(readLimit);
        number = bis.read();        //get 3
        number = bis.read();        //get 4

        bis.reset();

        number = bis.read();        //should get 3
        assertEquals(number, EXPECTED_NUMBER);
    }

    @Test
    void givenFileInputStream_whenReset_thenIOException() {
        assertThrows(IOException.class, () -> {
            FileInputStream fis = new FileInputStream(fileName);
            fis.read();
            fis.mark(readLimit);
            fis.read();
            fis.reset();
        });
    }

    @Test
    void givenBufferedInputStream_whenMarkSupported_thenTrue() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        boolean isMarkSupported = bis.markSupported();
        assertEquals(isMarkSupported, true);
    }

    @Test
    void givenBufferedInputStream_whenMarkAndReset_thenReadMarkedPosition() throws IOException {
        final char EXPECTED_CHAR = 'w';
        FileInputStream fis = new FileInputStream(fileName);
        //content:
        //All work and no play makes Jack a dull boy

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

    @Test
    void givenFileInputStream_whenMarkSupported_thenFalse() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        boolean isMarkSupported = fis.markSupported();
        assertEquals(isMarkSupported, false);
    }

    @Test
    void givenRandomAccessFile_whenSeek_thenMoveToIndicatedPosition() throws IOException {
        final char EXPECTED_CHAR = 'w';
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        //content:
        //All work and no play makes Jack a dull boy

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
