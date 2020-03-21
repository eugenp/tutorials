package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.MovieDataSourceAdapter;
import com.baeldung.architecture.hexagonal.adapter.MovieDisplayAdapter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSearch {
    private MovieDataSourceAdapter movieDataSourceAdapter;
    private MovieDisplayAdapter movieDisplayAdapter;

    private List<Movie> movies;

    public MovieSearch(MovieDataSourceAdapter movieDataSourceAdapter, MovieDisplayAdapter movieDisplayAdapter) {
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
