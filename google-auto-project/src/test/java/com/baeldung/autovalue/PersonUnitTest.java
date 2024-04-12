package com.baeldung.autovalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit Test which verifies that the {@link Person} value object creates defensive copies of its favoriteMovies list.
 */
public class PersonUnitTest {

    @Test
    public void givenNewPerson_whenModifyOriginalList_thenValueObjectIsNotAlsoModified() {
        // GIVEN new Person
        List<String> originalFavoriteMoviesList = new ArrayList<String>();
        originalFavoriteMoviesList.add("Training Day");
        originalFavoriteMoviesList.add("Fast and the Furious");
        Person person = Person.builder()
            .name("Dan")
            .favoriteMovies(originalFavoriteMoviesList)
            .build();

        // WHEN modify original list
        originalFavoriteMoviesList.add("Friday");

        // THEN Person remains unaffected
        assertFalse(person.favoriteMovies()
            .contains("Friday"));
        assertEquals(2, person.favoriteMovies()
            .size());
    }

}
