package com.baeldung.spring.modulith.cqrs.movie;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.modulith.cqrs.movie.domain.MovieRepository;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieScreens;

    MovieController(MovieRepository screenRooms) {
        this.movieScreens = screenRooms;
    }

    /*
        curl -X GET "http://localhost:8080/api/seating/movies?range=week"
    */
    @GetMapping
    List<UpcomingMovies> moviesToday(@RequestParam String range) {
        Instant endTime = endTime(range);
        return movieScreens.findUpcomingMoviesByStartTimeBetween(now(), endTime.truncatedTo(DAYS));
    }

    /*
        curl -X GET http://localhost:8080/api/movies/1/seats
    */
    @GetMapping("/{movieId}/seats")
    ResponseEntity<AvailableMovieSeats> movieSeating(@PathVariable Long movieId) {
        return ResponseEntity.of(movieScreens.findAvailableSeatsByMovieId(movieId));
    }

    private static Instant endTime(String range) {
        return switch (range) {
            case "day" -> now().plus(1, DAYS);
            case "week" -> now().plus(7, DAYS);
            case "month" -> now().plus(30, DAYS);
            default -> throw new IllegalArgumentException("Invalid range: " + range);
        };
    }
}
