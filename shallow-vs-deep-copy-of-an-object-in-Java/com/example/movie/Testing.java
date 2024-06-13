package com.example.movie;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class Testing {

    @Test
    public void testShallowCopy() {
        // Create and modify the original movie
        Movie originalMovie = new Movie();
        originalMovie.setTitle("Inception");
        List<String> actors = new ArrayList<>();
        actors.add("Leonardo DiCaprio");
        originalMovie.setActors(actors);

        // Create a shallow copy of the movie
        Movie copiedMovie = null;
        try {
            copiedMovie = (Movie) originalMovie.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // Ensure the copied movie is not null
        assert copiedMovie != null;

        // Modify the copied movie
        copiedMovie.getActors().add("Tom Hardy");

        // Check if the original movie is affected
        assertEquals("Original Movie should not be modified", 1, originalMovie.getActors().size());
        assertEquals("Original Movie should not be modified", "Inception", originalMovie.getTitle());

        // Check if the copied movie reflects the changes
        assertEquals("Copied Movie should have additional actor", 2, copiedMovie.getActors().size());
    }

    @Test
    public void testDeepCopy() {
        // Create and modify the original movie
        Movie originalMovie = new Movie();
        originalMovie.setTitle("Inception");
        List<String> actors = new ArrayList<>();
        actors.add("Leonardo DiCaprio");
        originalMovie.setActors(actors);

        // Create a deep copy of the movie
        Movie copiedMovie = originalMovie.deepCopy();

        // Ensure the copied movie is not null
        assert copiedMovie != null;

        // Modify the copied movie
        copiedMovie.getActors().add("Tom Hardy");

        // Check if the original movie remains unchanged
        assertEquals("Original Movie should not be modified", 1, originalMovie.getActors().size());
        assertEquals("Original Movie should not be modified", "Inception", originalMovie.getTitle());

        // Check if the copied movie reflects the changes
        assertEquals("Copied Movie should have additional actor", 2, copiedMovie.getActors().size());
    }
}
