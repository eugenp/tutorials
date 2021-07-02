package com.baeldung.hexagonalarchinjava.infrastracture.repository.cassandra;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchinjava.domain.Event;
import com.baeldung.hexagonalarchinjava.domain.Participant;
import com.baeldung.hexagonalarchinjava.domain.repository.EventRepository;

@Component
public class CassandraDbEventRepository implements EventRepository {

	private final CassandraEventRepository eventRepository;

	@Autowired
	public CassandraDbEventRepository(CassandraEventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Optional<Event> findById(UUID id) {
		Optional<EventEntity> eventEntity = eventRepository.findById(id);
		if (eventEntity.isPresent()) {
			Event event = new Event(eventEntity.get().getId(), eventEntity.get().getEventName(),
					eventEntity.get().getOrganiser(), eventEntity.get().getEventLocation(),
					eventEntity.get().getEventTime(), eventEntity.get().getMinAgeRestriction());

			eventEntity.get().getParticipants().forEach(participant -> {
				Participant participantToAdd = new Participant();
				participantToAdd.setName(participant.getName());
				participantToAdd.setId(participant.getParticipantId());
				participantToAdd.setContactNumber(participant.getContactNumber());
				participantToAdd.setAge(participant.getAge());
				participantToAdd.setEmail(participant.getEmail());

				event.addParticipant(participantToAdd);
			});

			return Optional.of(event);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void save(Event event) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setId(event.getId());
		eventEntity.setEventName(event.getEventName());
		eventEntity.setEventTime(event.getEventTime());
		eventEntity.setEventStatus(event.getStatus().toString());
		eventEntity.setParticipants(new ArrayList<>());

		event.getParticipants().forEach(participant -> {
			ParticipantEntity participantEntity = new ParticipantEntity(participant.getId(), participant.getEmail(),
					participant.getName(), participant.getContactNumber(), participant.getAge());

			eventEntity.getParticipants().add(participantEntity);
		});

		eventRepository.save(eventEntity);
	}

	@Override
	public void delete(UUID id) {
		Optional<EventEntity> eventEntity = eventRepository.findById(id);
		if (eventEntity.isPresent()) {
			eventRepository.delete(eventEntity.get());
		}
	}

}
