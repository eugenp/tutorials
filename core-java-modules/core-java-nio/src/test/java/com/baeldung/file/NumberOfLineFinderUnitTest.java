package com.baeldung.file;

import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingApacheCommonsIO;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingBufferedReader;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingGoogleGuava;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingLineNumberReader;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFileChannel;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFiles;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingNIOFilesReadAllLines;
import static com.baeldung.file.NumberOfLineFinder.getTotalNumberOfLinesUsingScanner;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberOfLineFinderUnitTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.txt";
    private static final int ACTUAL_LINE_COUNT = 45;

    @Test
    public void whenUsingBufferedReader_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingBufferedReader(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingLineNumberReader_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingLineNumberReader(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingScanner_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingScanner(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFiles_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingNIOFiles(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFilesReadAllLines_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingNIOFilesReadAllLines(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingNIOFileChannel_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingNIOFileChannel(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingApacheCommonsIO_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingApacheCommonsIO(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

    @Test
    public void whenUsingGoogleGuava_thenReturnTotalNumberOfLines() {
        int lines = getTotalNumberOfLinesUsingGoogleGuava(INPUT_FILE_NAME);
        assertEquals(ACTUAL_LINE_COUNT, lines);
    }

}
