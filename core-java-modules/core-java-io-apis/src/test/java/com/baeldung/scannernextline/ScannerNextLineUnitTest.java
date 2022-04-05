package com.baeldung.scannernextline;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class ScannerNextLineUnitTest {

    @Test
    public void whenReadingLines_thenCorrect() {
        String input = "Scanner\nTest\n";
        try (Scanner scanner = new Scanner(input)) {
            assertEquals("Scanner", scanner.nextLine());
            assertEquals("Test", scanner.nextLine());
        }
    }

    @Test
    public void whenReadingPartialLines_thenCorrect() {
        String input = "Scanner\n";
        try (Scanner scanner = new Scanner(input)) {
            scanner.useDelimiter("");
            scanner.next();
            assertEquals("canner", scanner.nextLine());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void givenNoNewLine_whenReadingNextLine_thenThrowNoSuchElementException() {
        try (Scanner scanner = new Scanner("")) {
            String result = scanner.nextLine();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void givenScannerIsClosed_whenReadingNextLine_thenThrowIllegalStateException() {
        Scanner scanner = new Scanner("");
        scanner.close();
        String result = scanner.nextLine();
    }
}
