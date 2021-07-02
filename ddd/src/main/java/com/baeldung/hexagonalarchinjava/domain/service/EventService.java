package com.baeldung.hexagonalarchinjava.domain.service;

import com.baeldung.hexagonalarchinjava.domain.Event;

import com.baeldung.hexagonalarchinjava.domain.Participant;

import java.util.UUID;

public interface EventService {

	UUID createEvent(Event event);

	void addParticipant(UUID id, Participant participant);

	void removeParticipant(UUID id, Participant participant);

	void cancelEvent(UUID id);
	
	void completeEvent(UUID id);
	
	void deleteEvent(UUID id);
}
