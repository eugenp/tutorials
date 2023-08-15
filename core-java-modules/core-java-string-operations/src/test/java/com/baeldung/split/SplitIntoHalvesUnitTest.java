package com.baeldung.split;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SplitIntoHalvesUnitTest {

    @Test
    public void givenAString_whenSplitInHalf_thenCorrectParts() {
        String hello = "Baeldung";
        int mid = hello.length() / 2;
        String[] parts = { hello.substring(0, mid), hello.substring(mid) };

        assertEquals("Bael", parts[0]);
        assertEquals("dung", parts[1]);
    }
}
