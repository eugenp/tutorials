package com.baeldung.equalshashcoderecords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class CustomRecordEqualsHashCode {
    @Test
    public void testDefaultEqualsOfRecords() {
        Person robert = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        Person mike = new Person("Mike", "ADAMS", "ABJDJ2883", "2001-01-02");
        assertNotEquals(robert, mike);
    }

    @Test
    public void testHashCodesOfRecords() {
        Person robert = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        Person robertCopy = new Person("Robert", "Frost", "HDHDB223", "2000-01-02");
        assertEquals(robert.hashCode(), robertCopy.hashCode());
    }

    @Test
    public void testMovieCustomEquality() {
        Movie movie1 = new Movie("The Batman", 2022, "WB");
        Movie movie2 = new Movie("The Batman", 2022, "Dreamworks");
        assertEquals(movie1, movie2);
        assertEquals(movie1.hashCode(), movie2.hashCode());
    }
}
