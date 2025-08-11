package com.baeldung.spring.modulith.cqrs.movie;

import java.time.Instant;

import org.jmolecules.architecture.cqrs.QueryModel;

@QueryModel
record UpcomingMovies(Long id, String title, Instant startTime) {
}