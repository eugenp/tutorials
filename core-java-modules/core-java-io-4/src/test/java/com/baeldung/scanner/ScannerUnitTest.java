package com.baeldung.scanner;

import org.junit.Test;

import java.util.*;
import java.util.regex.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerUnitTest {
    @Test public void givenScannerWithPattern_thenSkipUsingPattern() {
        String str = "Java scanner skip tutorial";
        // Instantiates Scanner
        Scanner sc = new Scanner(str);
        // By using skip(Pattern) method is to skip that meets the given pattern
        sc.skip(Pattern.compile(".ava"));

        assertEquals(" scanner skip tutorial", sc.nextLine());
        // Scanner closed
        sc.close();
    }

    @Test public void givenScannerWithString_thenSkipUsingStringPattern() {
        String str = "Java scanner skip tutorial";
        // Instantiates Scanner
        Scanner sc = new Scanner(str);
        // By using skip(String) method is to skip that meets the given
        // pattern constructed from the given String
        sc.skip("Java");

        assertEquals(" scanner skip tutorial", sc.nextLine());
        // Scanner closed
        sc.close();
    }
}