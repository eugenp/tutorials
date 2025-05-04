package com.baeldung.closingscanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

class ScannerTryWithResourcesUnitTest {

    @Test
    void givenUserName_whenGetGreetingMessage_thenReturnsWelcomeMessage() {
        String input = "Anees\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        String result;
        try (Scanner scanner = new Scanner(inputStream)) {
            ScannerTryWithResources example = new ScannerTryWithResources();
            result = example.getGreetingMessage(scanner);
        }

        assertEquals("Hi, Anees Welcome to Baeldung", result);
    }
}
