package clearscannerbuffer;


import org.junit.Test;

import java.util.Scanner;

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
    public void givenInput_whenUsingSkip_thenBufferCleared() {
        int number = scanner.nextInt();
        scanner.skip("\n");
        String text = scanner.nextLine();

        assertEquals(123, number);
        assertEquals("Hello World", text);
    }
}
