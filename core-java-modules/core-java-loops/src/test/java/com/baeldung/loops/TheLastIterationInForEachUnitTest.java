package com.baeldung.loops;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class TheLastIterationInForEachUnitTest {

    //@formatter:off
    private static final List<String> MOVIES = List.of(
      "Titanic",
      "The Deer Hunter",
      "Lord of the Rings",
      "One Flew Over the Cuckoo's Nest",
      "No Country For Old Men");
    //@formatter:on

    @Test
    void whenUsingForEach_thenGetTheLastElementAfterTheLoop() {
        String myLastMovie = "";
        for (String movie : MOVIES) {
            // ... work with movie
            myLastMovie = movie;
        }
        assertEquals("No Country For Old Men", myLastMovie);
    }

    @Test
    void whenLoopingWithIndexes_thenGetExpectedResult() {
        int size = MOVIES.size();
        String myLastMovie = null;
        for (int i = 0; i < size; i++) {
            String movie = MOVIES.get(i);
            // ... work with movie
            if (i == size - 1) {
                myLastMovie = movie;
            }
        }
        assertEquals("No Country For Old Men", myLastMovie);
    }

    @Test
    void whenUsingIntStream_thenGetExpectedResult() {
        int size = MOVIES.size();
        final Map<Integer, String> myLastMovie = new HashMap<>();
        IntStream.range(0, size)
            .forEach(idx -> {
                String movie = MOVIES.get(idx);
                // ... work with movie
                if (idx == size - 1) {
                    myLastMovie.put(idx, movie);
                }
            });
        assertEquals(1, myLastMovie.size());
        assertTrue(myLastMovie.containsKey(size - 1));
        assertTrue(myLastMovie.containsValue("No Country For Old Men"));
    }

    @Test
    void whenUsingCounter_thenGetExpectedResult() {
        int size = MOVIES.size();
        String myLastMovie = null;
        int cnt = 0;
        for (String movie : MOVIES) {
            // ... work with movie
            if (++cnt == size) {
                myLastMovie = movie;
            }
        }
        assertEquals("No Country For Old Men", myLastMovie);
    }

    @Test
    void whenUsingIterator_thenGetExpectedResult() {
        String movie;
        String myLastMovie = null;
        for (Iterator<String> it = MOVIES.iterator(); it.hasNext(); ) {
            movie = it.next();
            // ... work with movie
            if (!it.hasNext()) { // the last element
                myLastMovie = movie;
            }
        }
        assertEquals("No Country For Old Men", myLastMovie);
    }
}