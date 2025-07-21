package com.baeldung.spring.modulith.cqrs.movie.seating;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.modulith.cqrs.movie.seating.api.AvailableSeats;
import com.baeldung.spring.modulith.cqrs.movie.seating.api.UpcomingMovies;

@Transactional(readOnly = true)
public interface ScreenRoomQueries {
    Optional<AvailableSeats> findAvailableSeatsByMovieId(Long movieId);
    List<UpcomingMovies> findByStartTimeBetweenOrderByStartTime(Instant start, Instant end);
}
