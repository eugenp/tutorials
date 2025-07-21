package com.baeldung.spring.modulith.cqrs.movie.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.modulith.cqrs.movie.AvailableSeats;

interface MovieRepository extends JpaRepository<Movie, Long>, MovieQueries {

    @Override
    default Optional<AvailableSeats> findAvailableSeatsByMovieId(Long movieId) {
        return findById(movieId).map(movie -> new AvailableSeats(movie.title(), movie.screenRoom(), movie.startTime(), movie.freeSeats()));
    }
}
