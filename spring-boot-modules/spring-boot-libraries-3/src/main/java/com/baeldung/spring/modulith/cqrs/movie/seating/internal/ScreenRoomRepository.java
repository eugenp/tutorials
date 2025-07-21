package com.baeldung.spring.modulith.cqrs.movie.seating.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.spring.modulith.cqrs.movie.seating.ScreenRoomQueries;
import com.baeldung.spring.modulith.cqrs.movie.seating.api.AvailableSeats;

interface ScreenRoomRepository extends JpaRepository<ScreenRoom, Long>, ScreenRoomQueries {

    Optional<ScreenRoom> findByMovieId(Long movieId);

    @Override
    default Optional<AvailableSeats> findAvailableSeatsByMovieId(Long movieId) {
        return findByMovieId(movieId)
            .map(room -> new AvailableSeats(room.getMovieName(), room.getStartTime(), room.getFreeSeats()));
    }
}
