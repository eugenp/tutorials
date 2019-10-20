package com.baeldung.scanner;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;

public class JavaScannerUnitTest {

    @Test
    public void whenReadingLines_thenCorrect() {
        String input = "Scanner\nTest\n";

        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        //@formatter:off
        try (InputStream is = new ByteArrayInputStream(byteArray);
             Scanner scanner = new Scanner(is)) {

            //@formatter:on
            String result = scanner.nextLine() + " " + scanner.nextLine();

            String expected = input.replace("\n", " ")
                    .trim();
            assertEquals(expected, result);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void whenReadingLinesFromStringContainingNoLines_thenThrowNoSuchElementException() {
        String input = "";
        Scanner scanner = new Scanner(input);
        String result = scanner.nextLine();
        scanner.close();
    }

    @Test(expected = IllegalStateException.class)
    public void whenReadingLinesUsingClosedScanner_thenThrowIllegalStateException() {
        String input = "";
        Scanner scanner = new Scanner(input);
        scanner.close();
        String result = scanner.nextLine();
    }


}
