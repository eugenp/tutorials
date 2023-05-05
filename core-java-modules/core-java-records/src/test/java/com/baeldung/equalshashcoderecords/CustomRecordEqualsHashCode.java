package com.baeldung.equalshashcoderecords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class CustomRecordEqualsHashCode {
    @Test
    public void givenTwoRecords_whenDefaultEquals_thenCompareEquality() {
        Person robert = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        Person mike = new Person("Mike", "Adams", "ABJDJ2883", "2001-01-02");
        assertNotEquals(robert, mike);
    }

    @Test
    public void givenTwoRecords_hashCodesShouldBeSame() {
        Person robert = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        Person robertCopy = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        assertEquals(robert.hashCode(), robertCopy.hashCode());
    }

    @Test
    public void givenTwoRecords_whenCustomImplementation_thenCompareEquality() {
        Movie movie1 = new Movie("The Batman", 2022, "WB");
        Movie movie2 = new Movie("The Batman", 2022, "Dreamworks");
        assertEquals(movie1, movie2);
        assertEquals(movie1.hashCode(), movie2.hashCode());
    }
}
