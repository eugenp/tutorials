package com.baeldung.hexagonalarchinjava.domain.service;

import com.baeldung.hexagonalarchinjava.domain.Event;
import com.baeldung.hexagonalarchinjava.domain.Participant;
import com.baeldung.hexagonalarchinjava.domain.repository.EventRepository;

import java.util.Optional;
import java.util.UUID;

public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	public EventServiceImpl(final EventRepository orderRepository) {
		this.eventRepository = orderRepository;
	}

	@Override
	public UUID createEvent(final Event event) {
		eventRepository.save(event);

		return event.getId();
	}

	@Override
	public void addParticipant(UUID id, Participant participant) {
		Optional<Event> event = eventRepository.findById(id);

		if (event.isPresent()) {
			event.get().addParticipant(participant);

			eventRepository.save(event.get());
		}
	}

	@Override
	public void removeParticipant(UUID id, Participant participant) {
		Optional<Event> event = eventRepository.findById(id);

		if (event.isPresent()) {
			event.get().removeParticipant(participant);

			eventRepository.save(event.get());
		}
	}

	@Override
	public void cancelEvent(UUID id) {
		Optional<Event> event = eventRepository.findById(id);

		if (event.isPresent()) {
			event.get().cancelEvent();

			eventRepository.save(event.get());
		}
	}

	@Override
	public void deleteEvent(UUID id) {
		eventRepository.delete(id);
	}

	@Override
	public void completeEvent(UUID id) {
		Optional<Event> event = eventRepository.findById(id);

		if (event.isPresent()) {
			event.get().completeEvent();

			eventRepository.save(event.get());
		}

	}

}
