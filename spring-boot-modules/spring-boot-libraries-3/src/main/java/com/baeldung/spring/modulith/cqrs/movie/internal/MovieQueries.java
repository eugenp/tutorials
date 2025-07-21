package com.baeldung.spring.modulith.cqrs.movie.internal;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.modulith.cqrs.movie.AvailableSeats;
import com.baeldung.spring.modulith.cqrs.movie.UpcomingMovies;

@Transactional(readOnly = true)
public interface MovieQueries {
    Optional<AvailableSeats> findAvailableSeatsByMovieId(Long movieId);
    List<UpcomingMovies> findUpcomingMoviesByStartTimeBetween(Instant start, Instant end);
}
