package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.port.MovieDataSource;
import com.baeldung.architecture.hexagonal.port.MovieDisplay;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSearch {
    private MovieDataSource movieDataSourceAdapter;
    private MovieDisplay movieDisplayAdapter;

    private List<Movie> movies;

    public MovieSearch(MovieDataSource movieDataSourceAdapter, MovieDisplay movieDisplayAdapter) {
        this.movieDataSourceAdapter = movieDataSourceAdapter;
        this.movieDisplayAdapter = movieDisplayAdapter;
    }

    /**
     * Search by a movie title
     * @param movieTitle
     */
    public void search(final String movieTitle) throws IOException {
        this.movies = this.movieDataSourceAdapter.allMovies();
        List<Movie> matchedMovies = this.movies.stream().filter(m->m.getTitle().contains(movieTitle)).collect(Collectors.toList());
        this.movieDisplayAdapter.display(matchedMovies);
    }
}
