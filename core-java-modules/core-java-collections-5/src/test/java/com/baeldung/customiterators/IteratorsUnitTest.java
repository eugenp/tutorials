package com.baeldung.customiterators;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IteratorsUnitTest {
    @Test
    public void givenListOfStrings_whenIteratedWithDefaultIterator() {
        List<String> listOfStrings = List.of("hello", "world", "this", "is", "a", "test");
        Iterator<String> iterator = listOfStrings.iterator();
        Assert.assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), "hello");
    }

    @Test
    public void givenListOfStrings_whenPalindromIterator_thenOnlyPalindromes() {
        List<String> listOfStrings = List.of("oslo", "madam", "car", "deed", "wow", "test");
        PalindromIterator palindromIterator = new PalindromIterator(listOfStrings);
        int count = 0;
        while (palindromIterator.hasNext()) {
            palindromIterator.next();
            count++;
        }
        assertEquals(count, 3);
    }

    @Test
    public void givenMovieList_whenMovieIteratorUsed_thenOnlyHighRatedMovies() {
        List<Movie> movies = getMovies();
        CustomMovieIterator movieIterator = new CustomMovieIterator(movies);
        int count = 0;
        while (movieIterator.hasNext()) {
            movieIterator.next();
            count++;
        }
        assertEquals(4, movies.size());
        assertEquals(2, count);
    }

    private List<Movie> getMovies() {
        Movie movie1 = new Movie("The Dark Knight", "Nolan", 10);
        Movie movie2 = new Movie("Avatar", "Cameron", 9);
        Movie movie3 = new Movie("Tenet", "Nolan", 7);
        Movie movie4 = new Movie("Extraction", "Hargrave", 5);
        return List.of(movie1, movie2, movie3, movie4);
    }

}
