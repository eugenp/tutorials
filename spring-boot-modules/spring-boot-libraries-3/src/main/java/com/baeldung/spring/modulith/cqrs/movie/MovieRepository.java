package com.baeldung.spring.modulith.cqrs.movie;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

interface MovieRepository extends CrudRepository<Movie, Long> {

    List<UpcomingMovie> findUpcomingMoviesByStartTimeBetween(Instant start, Instant end);

    default Optional<AvailableMovieSeats> findAvailableSeatsByMovieId(Long movieId) {
        return findById(movieId).map(movie -> new AvailableMovieSeats(movie.title(), movie.screenRoom(), movie.startTime(), movie.freeSeats()));
    }
}
