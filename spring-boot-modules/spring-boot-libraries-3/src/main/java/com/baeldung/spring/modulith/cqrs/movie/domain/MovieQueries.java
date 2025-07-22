package com.baeldung.spring.modulith.cqrs.movie.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.baeldung.spring.modulith.cqrs.movie.AvailableMovieSeats;
import com.baeldung.spring.modulith.cqrs.movie.UpcomingMovies;

public interface MovieQueries {

    Optional<AvailableMovieSeats> findAvailableSeatsByMovieId(Long movieId);

    List<UpcomingMovies> findUpcomingMoviesByStartTimeBetween(Instant start, Instant end);
}
