package com.baeldung.hexagonal.repository.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.repository.impl.MongoDbMovieRepository;

public class MongoDbMovieRepositoryUnitTest {
    private static MongoDbMovieRepository mongoDbMovieRepository;

    @BeforeClass
    public static void beforeAll() throws UnknownHostException, IOException {
        mongoDbMovieRepository = new MongoDbMovieRepository();
    }

    @AfterClass
    public static void done() {
        mongoDbMovieRepository.shutDown();
    }

    @Test
    public void whenMovieIsSaved_ThenVerifyIfItCanBeFoundById() {
        final UUID uuid = UUID.randomUUID();
        final Movie movie = new Movie(uuid, "LOTR: The fellowship of the ring");
        movie.addRating("Yogesh", 10);
        movie.addRating("Jiya", 6);
        mongoDbMovieRepository.save(movie);

        final Movie actual = mongoDbMovieRepository.findById(uuid);
        assertThat(actual.getName(), is(movie.getName()));
        assertThat(actual.getAllRatings()
            .size(),
            is(movie.getAllRatings()
                .size()));

        mongoDbMovieRepository.delete(uuid);
    }

    @Test
    public void whenMovieIsSaved_ThenVerifyItCanBeSearchedByName() {
        final UUID uuid1 = UUID.randomUUID();
        final Movie movie1 = new Movie(uuid1, "LOTR: The fellowship of the ring");
        mongoDbMovieRepository.save(movie1);
        final UUID uuid2 = UUID.randomUUID();
        final Movie movie2 = new Movie(uuid2, "LOTR: The two towers");
        mongoDbMovieRepository.save(movie2);

        final Set<Movie> movies = mongoDbMovieRepository.searchMoviesByName("LOTR");
        assertThat(movies.size(), is(2));

        mongoDbMovieRepository.delete(uuid1);
        mongoDbMovieRepository.delete(uuid2);
    }
}
