package com.baeldung.enums.comparestrenum;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static com.baeldung.enums.comparestrenum.Weekday.Fri;
import static com.baeldung.enums.comparestrenum.Weekday.Sat;
import static org.junit.jupiter.api.Assertions.*;

enum Weekday {
    Mon("Monday"),
    Tue("Tuesday"),
    Wed("Wednesday"),
    Thu("Thursday"),
    Fri("Friday"),
    Sat("Saturday");

    private String fullName;

    Weekday(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }


    static Optional<Weekday> byFullNameIgnoreCase(String givenFullName) {
        return Arrays.stream(values()).filter(it -> it.fullName.equalsIgnoreCase(givenFullName)).findAny();
    }

    static Optional<Weekday> byNameIgnoreCase(String givenName) {
        return Arrays.stream(values()).filter(it -> it.name().equalsIgnoreCase(givenName)).findAny();
    }
}

public class CompareStringAndEnumUnitTest {
    private static final String SAT = "sAt";
    private static final String SATURDAY = "sAtuRdAy";
    private static final String TYPO_FRI = "ffri";
    private static final String TYPO_FRIDAY = "ffriday";

    @Test
    void givenAString_whenCompareEnumWithName_thenGetExpectedResult() {
        assertTrue(SAT.equalsIgnoreCase(Sat.name()));
        assertFalse(TYPO_FRI.equalsIgnoreCase(Fri.name()));
    }

    @Test
    void givenAString_whenCompareEnumWithProperty_thenGetExpectedResult() {
        assertTrue(SATURDAY.equalsIgnoreCase(Sat.getFullName()));
        assertFalse(TYPO_FRI.equalsIgnoreCase(Fri.getFullName()));
    }

    @Test
    void givenAString_whenFindEnumByName_thenGetExpectedResult() {
        Optional<Weekday> optResult = Weekday.byNameIgnoreCase(SAT);
        assertTrue(optResult.isPresent());
        assertEquals(Sat, optResult.get());

        Optional<Weekday> optResult2 = Weekday.byNameIgnoreCase(TYPO_FRI);
        assertFalse(optResult2.isPresent());
    }

    @Test
    void givenAString_whenFindEnumByProperty_thenGetExpectedResult() {
        Optional<Weekday> optResult = Weekday.byFullNameIgnoreCase(SATURDAY);
        assertTrue(optResult.isPresent());
        assertEquals(Sat, optResult.get());

        Optional<Weekday> optResult2 = Weekday.byFullNameIgnoreCase(TYPO_FRIDAY);
        assertFalse(optResult2.isPresent());
    }

}