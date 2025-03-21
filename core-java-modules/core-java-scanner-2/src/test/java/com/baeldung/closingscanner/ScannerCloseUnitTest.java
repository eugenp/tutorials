package com.baeldung.closingscanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ScannerCloseUnitTest {
    @Test
    void givenUserName_whenGetGreetingMessage_thenReturnsWelcomeMessage() {
        String input = "Anees\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ScannerClose example = new ScannerClose();
        String result = example.getGreetingMessage(scanner);

        assertEquals("Hi, Anees Welcome to Baeldung", result);

        scanner.close();
    }
}
