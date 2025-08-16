package com.baeldung.spring.modulith.cqrs.movie;

import java.time.Instant;
import java.util.List;

import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
record AvailableMovieSeats(String title, String screenRoom, Instant startTime, List<String> freeSeats) {

}