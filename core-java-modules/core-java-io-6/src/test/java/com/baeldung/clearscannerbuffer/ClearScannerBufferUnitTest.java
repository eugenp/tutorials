package clearscannerbuffer;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ClearScannerBufferUnitTest {
    Scanner scanner = new Scanner("123\nHello World");

    @Test
    public void givenInput_whenUsingNextLineWithHasNextLine_thenBufferCleared() {

        int number = scanner.nextInt();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        String text = scanner.nextLine();

        assertEquals(123, number);
        assertEquals("Hello World", text);
    }

    @Test
    public void givenInput_whenUsingReset_thenBufferCleared() {
        String input = "123\nHello World";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        int number = scanner.nextInt();

        inputStream.reset();
        scanner = new Scanner(inputStream);

        int newNumber = scanner.nextInt();

        scanner.nextLine();

        String text = scanner.nextLine();

        assertEquals(123, number);
        assertEquals(123, newNumber);
        assertEquals("Hello World", text);
    }

    @Test
    public void givenInput_whenUsingStreamAPI_thenBufferCleared() {
        Scanner scanner = new Scanner("123\nline1\nline2\nexit");

        int number = scanner.nextInt();

        scanner.nextLine();

        String lines = Stream.generate(scanner::nextLine)
                .takeWhile(line -> !line.equalsIgnoreCase("exit"))
                .collect(Collectors.joining(System.lineSeparator()));

        String expectedLines = "line1" + System.lineSeparator() + "line2";

        assertEquals(123, number);
        assertEquals(expectedLines, lines);
    }
}