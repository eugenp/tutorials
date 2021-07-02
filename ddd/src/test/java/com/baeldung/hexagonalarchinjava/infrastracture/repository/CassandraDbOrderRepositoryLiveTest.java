package com.baeldung.hexagonalarchinjava.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.baeldung.hexagonalarchinjava.domain.Event;
import com.baeldung.hexagonalarchinjava.domain.Participant;
import com.baeldung.hexagonalarchinjava.domain.repository.EventRepository;
import com.baeldung.hexagonalarchinjava.infrastracture.repository.cassandra.CassandraEventRepository;

@SpringJUnitConfig
@SpringBootTest
@TestPropertySource("classpath:ddd-layers-test.propertiess")
class CassandraDbEventRepositoryLiveTest {

	@Autowired
	private CassandraEventRepository cassandraEventRepository;

	@Autowired
	private EventRepository eventRepository;

	@AfterEach
	void cleanUp() {
		cassandraEventRepository.deleteAll();
		;
	}

	@Test
	void shouldFindById_thenReturnEvent() {

		// given
		final UUID id = UUID.randomUUID();
		final Event event = createEvent(id);

		event.addParticipant(this.createNewParticipant());

		// when
		eventRepository.save(event);

		final Optional<Event> result = eventRepository.findById(id);

		assertEquals(event, result.get());
	}

	@Test
	void shouldFindById_WhenParticipantIsRemoved_thenReturnEvent() {

		// given
		final UUID id = UUID.randomUUID();
		final Event event = createEvent(id);

		Participant participant = this.createNewParticipant();
		event.addParticipant(participant);

		eventRepository.save(event);

		event.removeParticipant(participant);

		eventRepository.save(event);

		final Optional<Event> result = eventRepository.findById(id);

		assertEquals(event, result.get());
	}

	private Event createEvent(UUID id) {
		return new Event(id, "test", "test-user", "test-location", LocalDateTime.now().plusDays(30), 18);
	}

	private Participant createNewParticipant() {
		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(20);

		return part;
	}
}