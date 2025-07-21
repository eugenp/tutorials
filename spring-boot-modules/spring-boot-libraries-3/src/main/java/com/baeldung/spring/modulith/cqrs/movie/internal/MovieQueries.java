package com.baeldung.spring.modulith.cqrs.movie.internal;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.modulith.cqrs.movie.AvailableMovieSeats;
import com.baeldung.spring.modulith.cqrs.movie.UpcomingMovies;

@Transactional(readOnly = true)
public interface MovieQueries {
    Optional<AvailableMovieSeats> findAvailableSeatsByMovieId(Long movieId);
    List<UpcomingMovies> findUpcomingMoviesByStartTimeBetween(Instant start, Instant end);
}
