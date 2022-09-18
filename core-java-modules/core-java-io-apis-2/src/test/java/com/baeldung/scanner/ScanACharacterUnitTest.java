package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class ScanACharacterUnitTest {

    String INPUT = new StringBuilder().append("abc\n")
      .append("mno\n")
      .append("xyz\n")
      .toString();

    @Test
    public void testReadCharUsingNext() {
        Scanner sc = new Scanner(INPUT);
        char c = sc.next().charAt(0);
        assertEquals('a', c);
    }

    @Test
    public void testReadCharUsingFindInLine() {
        Scanner sc = new Scanner(INPUT);
        char c = sc.findInLine(".").charAt(0);
        assertEquals('a', c);
    }

    @Test
    public void testReadCharUsingUseDelimiter() {
        Scanner sc = new Scanner(INPUT);
        String c = sc.useDelimiter("").next();
        assertEquals("a", c);
    }

}
