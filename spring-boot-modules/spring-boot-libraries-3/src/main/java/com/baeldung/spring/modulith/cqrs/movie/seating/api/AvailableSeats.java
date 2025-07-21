package com.baeldung.spring.modulith.cqrs.movie.seating.api;

import java.time.Instant;
import java.util.List;

import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
public record AvailableSeats(String movieName, Instant startTime, List<String> freeSeats) {
}