package com.baeldung.scannerclose;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ScannerExampleUnitTest {
	@Test
	void givenUserName_whenGetGreetingMessage_thenReturnsWelcomeMessage() {
		String simulatedInput = "Anees\n";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
		Scanner scanner = new Scanner(inputStream);

		ScannerExample example = new ScannerExample();
		String result = example.getGreetingMessage(scanner);

		assertEquals("Hi, Anees Welcome to Baeldung", result);

		scanner.close();
	}
}
