package com.baeldung.scanner;

import org.junit.Test;

import java.util.*;
import java.util.regex.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerUnitTest {
    @Test public void scannerSkipUsingPattern() {
        String str = "Java scanner skip tutorial";
        // Instantiates Scanner
        Scanner sc = new Scanner(str);
        // By using skip(Pattern) method is to skip that meets the given pattern
        sc.skip(Pattern.compile(".ava"));
        assertEquals(sc.nextLine(), " scanner skip tutorial");
        // Scanner closed
        sc.close();
    }

    @Test public void scannerSkipUsingStringPattern() {
        String str = "Java scanner skip tutorial";
        // Instantiates Scanner
        Scanner sc = new Scanner(str);
        // By using skip(String) method is to skip that meets the given
        // pattern constructed from the given String
        sc.skip("Java");
        assertEquals(sc.nextLine(), " scanner skip tutorial");

        // Scanner closed
        sc.close();
    }
}