package com.baeldung.hexagonalarchinjava.domain.repository;

import com.baeldung.hexagonalarchinjava.domain.Event;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
	Optional<Event> findById(UUID id);

	void save(Event event);

	void delete(UUID id);
	
}
