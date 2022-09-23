package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class ScanACharacterUnitTest {

    // given - input scanner source, no need to scan from console
    String Input = new StringBuilder().append("abc\n")
      .append("mno\n")
      .append("xyz\n")
      .toString();

    @Test
    public void givenInputSource_whenScanCharUsingNext_thenOneCharIsRead() {
        Scanner sc = new Scanner(Input);
        char c = sc.next().charAt(0);
        assertEquals('a', c);
    }

    @Test
    public void givenInputSource_whenScanCharUsingFindInLine_thenOneCharIsRead() {
        Scanner sc = new Scanner(Input);
        char c = sc.findInLine(".").charAt(0);
        assertEquals('a', c);
    }

    @Test
    public void givenInputSource_whenScanCharUsingUseDelimiter_thenOneCharIsRead() {
        Scanner sc = new Scanner(Input);
        String c = sc.useDelimiter("").next();
        assertEquals("a", c);
    }

}
