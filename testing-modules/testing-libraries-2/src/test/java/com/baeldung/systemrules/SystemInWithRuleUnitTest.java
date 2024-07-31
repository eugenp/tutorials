package com.baeldung.systemrules;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class SystemInWithRuleUnitTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void givenTwoNames_whenSystemInMock_thenNamesJoinedTogether() {
        systemInMock.provideLines("Jonathan", "Cook");
        assertEquals("Names should be concatenated", "Jonathan Cook", getFullname());
    }

    private String getFullname() {
        try (Scanner scanner = new Scanner(System.in)) {
            String firstName = scanner.next();
            String surname = scanner.next();
            return String.join(" ", firstName, surname);
        }
    }

}
