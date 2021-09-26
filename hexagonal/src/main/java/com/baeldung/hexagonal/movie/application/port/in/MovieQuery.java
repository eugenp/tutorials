package com.baeldung.hexagonal.movie.application.port.in;

import java.util.List;

import com.baeldung.hexagonal.movie.domain.Genre;
import com.baeldung.hexagonal.movie.domain.Movie;
import com.baeldung.hexagonal.movie.domain.Price;

public interface MovieQuery {
    List<Movie> getMovieByGenre(Genre genre);
    List<Movie> getMovieBetweenRentPrice(Price low, Price high);
}
