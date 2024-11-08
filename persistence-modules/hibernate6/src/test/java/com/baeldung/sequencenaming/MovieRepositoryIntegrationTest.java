package com.baeldung.sequencenaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void testCreateAndRetrieveMovieWithManualId() {
        Movie movie = new Movie("MOVIE-1", "Inception", "Christopher Nolan");
        Movie savedMovie = movieRepository.save(movie);
        assertEquals("MOVIE-1", savedMovie.getId());
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals("Christopher Nolan", savedMovie.getDirector());
    }

    @Test
    void testRetrieveMovieByManualId() {
        Movie movie = new Movie("MOVIE-2", "The Shawshank Redemption", "Frank Darabont");
        movieRepository.save(movie);
        Movie retrievedMovie = movieRepository.findById("MOVIE-2").orElse(null);
        assertNotNull(retrievedMovie);
        assertEquals("The Shawshank Redemption", retrievedMovie.getTitle());
        assertEquals("Frank Darabont", retrievedMovie.getDirector());
        assertEquals("MOVIE-2", retrievedMovie.getId());
    }

    @Test
    void testPreventIdGenerationWhenManualIdAssigned() {
        Movie movie1 = new Movie("MOVIE-3", "The Matrix", "Wachowski Brothers");
        movieRepository.save(movie1);
        Movie movie2 = new Movie("MOVIE-4", "Interstellar", "Christopher Nolan");
        movieRepository.save(movie2);
        Movie retrievedMovie1 = movieRepository.findById("MOVIE-3").orElse(null);
        Movie retrievedMovie2 = movieRepository.findById("MOVIE-4").orElse(null);
        assertNotNull(retrievedMovie1);
        assertNotNull(retrievedMovie2);
        assertEquals("MOVIE-3", retrievedMovie1.getId());
        assertEquals("MOVIE-4", retrievedMovie2.getId());
    }
}
