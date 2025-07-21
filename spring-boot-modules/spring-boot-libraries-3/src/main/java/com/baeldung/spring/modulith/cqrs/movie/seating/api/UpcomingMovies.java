package com.baeldung.spring.modulith.cqrs.movie.seating.api;

import java.time.Instant;
import java.util.List;

import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public record UpcomingMovies(Long movieId, String movieName, Instant startTime) {
}