package com.baeldung.scanner;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Test;

public class JavaScannerUnitTest {

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
    public void whenReadingLines_thenThrowNoSuchElementException() {
        try (Scanner scanner = new Scanner("")) {
            String result = scanner.nextLine();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void whenReadingLines_thenThrowIllegalStateException() {
        Scanner scanner = new Scanner("");
        scanner.close();
        String result = scanner.nextLine();
    }
}
