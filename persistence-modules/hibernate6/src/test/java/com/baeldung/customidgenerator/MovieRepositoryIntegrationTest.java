package com.baeldung.customidgenerator;

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
    void givenMovie_whenCreatingAndRetrievingMovie_thenCorrectMovieIsRetrieved() {
        Movie movie = new Movie("3 Idiots", "Rajkumar Hirani");
        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(savedMovie.getId());
        assertEquals("3 Idiots", savedMovie.getTitle());
        assertEquals("Rajkumar Hirani", savedMovie.getDirector());
    }

    @Test
    void givenManualId_whenCreatingAndRetrievingMovie_thenCorrectMovieIsRetrieved() {
        Movie movie = new Movie(10L, "Inception", "Christopher Nolan");
        Movie savedMovie = movieRepository.save(movie);
        assertEquals(10L, savedMovie.getId());
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals("Christopher Nolan", savedMovie.getDirector());
    }

    @Test
    void givenExistingMovieWithManualId_whenRetrievingById_thenCorrectMovieIsFound() {
        Movie movie = new Movie(2L, "The Shawshank Redemption", "Frank Darabont");
        movieRepository.save(movie);
        Movie retrievedMovie = movieRepository.findById(2L).orElse(null);
        assertNotNull(retrievedMovie);
        assertEquals("The Shawshank Redemption", retrievedMovie.getTitle());
        assertEquals("Frank Darabont", retrievedMovie.getDirector());
        assertEquals(2L, retrievedMovie.getId());
    }

    @Test
    void givenManualIds_whenSavingMultipleMovies_thenIdsAreNotOverwritten() {
        Movie movie1 = new Movie(3L, "The Matrix", "Wachowski Brothers");
        movieRepository.save(movie1);
        Movie movie2 = new Movie(4L, "Interstellar", "Christopher Nolan");
        movieRepository.save(movie2);
        Movie retrievedMovie1 = movieRepository.findById(3L).orElse(null);
        Movie retrievedMovie2 = movieRepository.findById(4L).orElse(null);
        assertNotNull(retrievedMovie1);
        assertNotNull(retrievedMovie2);
        assertEquals(3L, retrievedMovie1.getId());
        assertEquals(4L, retrievedMovie2.getId());
    }
}
