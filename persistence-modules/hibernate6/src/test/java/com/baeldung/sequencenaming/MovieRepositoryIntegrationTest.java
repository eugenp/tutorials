package com.baeldung.sequencenaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class MovieRepositoryIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveMovie() {
        Movie movie = new Movie(null, "Inception", "Christopher Nolan");

        Movie savedMovie = movieRepository.save(movie);

        assertNotNull(savedMovie.getId()); // Ensure ID is generated
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals("Christopher Nolan", savedMovie.getDirector());
    }

    @Test
    void testUniqueIdGeneration() {
        Movie movie1 = new Movie(null, "The Matrix", "Wachowski Brothers");
        Movie movie2 = new Movie(null, "Interstellar", "Christopher Nolan");

        Movie savedMovie1 = movieRepository.save(movie1);
        Movie savedMovie2 = movieRepository.save(movie2);

        assertNotNull(savedMovie1.getId());
        assertNotNull(savedMovie2.getId());
        assertNotEquals(savedMovie1.getId(), savedMovie2.getId()); // Ensure unique IDs
    }

    @Test
    void testRetrieveMovieById() {
        Movie movie = new Movie(null, "The Shawshank Redemption", "Frank Darabont");
        Movie savedMovie = movieRepository.save(movie);

        Movie retrievedMovie = movieRepository.findById(savedMovie.getId()).orElse(null);

        assertNotNull(retrievedMovie);
        assertEquals("The Shawshank Redemption", retrievedMovie.getTitle());
        assertEquals("Frank Darabont", retrievedMovie.getDirector());
    }
}
