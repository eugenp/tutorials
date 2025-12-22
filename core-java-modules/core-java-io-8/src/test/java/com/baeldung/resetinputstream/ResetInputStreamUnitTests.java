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

    final String fileName = "src/test/resources/InputStreamData.txt";

    @Test
    void givenByteArrayInputStream_whenMarkSupported_thenTrue() {
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        boolean isMarkSupported = bis.markSupported();
        assertEquals(true, isMarkSupported);
    }

    @Test
    void givenByteArrayInputStream_whenMarkAndReset_thenReadMarkedPosition() {
        final int EXPECTED_NUMBER = 3;
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        int number = bis.read();    //get 1
        number = bis.read();        //get 2
        bis.mark(0);                //irrelevant for ByteArrayInputStream
        number = bis.read();        //get 3
        number = bis.read();        //get 4

        bis.reset();

        number = bis.read();        //should get 3
        assertEquals(EXPECTED_NUMBER, number);
    }

    @Test
    void givenFileInputStream_whenReset_thenIOException() {
        final int readLimit = 500;
        assertThrows(IOException.class, () -> {
            FileInputStream fis = new FileInputStream(fileName);
            fis.read();
            fis.mark(readLimit);
            fis.read();
            fis.reset();
        });
    }

    @Test
    void givenFileInputStream_whenMarkSupported_thenFalse() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        boolean isMarkSupported = fis.markSupported();
        assertEquals(false, isMarkSupported);
    }

    @Test
    void givenBufferedInputStream_whenMarkSupported_thenTrue() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        boolean isMarkSupported = bis.markSupported();
        assertEquals(true, isMarkSupported);
    }

    @Test
    void givenBufferedInputStream_whenMarkAndReset_thenReadMarkedPosition() throws IOException {
        final int readLimit = 500;
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
        assertEquals(EXPECTED_CHAR, test);
    }

    @Test
    void givenBufferedInputStream_whenMarkIsInvalid_thenIOException() throws IOException {
        final int bufferSize = 2;
        final int readLimit = 1;
        assertThrows(IOException.class, () -> {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis, bufferSize); // constructor accepting buffer size
            bis.read();
            bis.mark(readLimit);
            bis.read();
            bis.read();
            bis.read();  // this read exceeds both read limit and buffer size

            bis.reset(); // mark position is invalid
        });
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
        assertEquals(EXPECTED_CHAR, test);
    }
}
