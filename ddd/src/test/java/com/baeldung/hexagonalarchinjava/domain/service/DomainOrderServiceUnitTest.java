package com.baeldung.hexagonalarchinjava.domain.service;

import com.baeldung.hexagonalarchinjava.domain.Event;
import com.baeldung.hexagonalarchinjava.domain.Participant;
import com.baeldung.hexagonalarchinjava.domain.repository.EventRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceUnitTest {

	private EventRepository eventRepository;
	private EventService tested;

	@BeforeEach
	void setUp() {
		eventRepository = mock(EventRepository.class);
		tested = new EventServiceImpl(eventRepository);
	}

	@Test
	void shouldCreateEvent_thenSaveIt() {

		final Event event = EventServiceUnitTest.createEvent(UUID.randomUUID());

		final UUID id = tested.createEvent(event);

		verify(eventRepository).save(any(Event.class));
		assertNotNull(id);
	}

	@Test
	void shouldAddParticipant_thenSaveEvent() {
		final Event event = EventServiceUnitTest.createEvent(UUID.randomUUID());

		final UUID id = tested.createEvent(event);

		assertNotNull(id);

		verify(eventRepository).save(any(Event.class));

		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(20);

		tested.addParticipant(id, part);

		verify(eventRepository).save(any(Event.class));
	}

	@Test
	void shouldAddProduct_thenThrowException() {
		Event event = EventServiceUnitTest.createEvent(UUID.randomUUID());

		Mockito.when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));

		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(16);

		final Executable executable = () -> tested.addParticipant(event.getId(), part);

		verify(eventRepository, times(0)).save(any(Event.class));
		assertThrows(RuntimeException.class, executable);
	}

	@Test
	void shouldCompleteOrder_thenSaveIt() {
		Event event = EventServiceUnitTest.createEvent(UUID.randomUUID());

		Mockito.when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));

		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(16);

		tested.completeEvent(event.getId());

		verify(eventRepository).save(any(Event.class));
	}

	@Test
	void shouldDeleteEvent_thenSaveOrder() {
		Event event = EventServiceUnitTest.createEvent(UUID.randomUUID());

		Mockito.when(eventRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(event));

		tested.deleteEvent(event.getId());

		verify(eventRepository).delete(event.getId());

	}

	private static Event createEvent(UUID id) {
		return new Event(id, "test", "test-user", "test-location", LocalDateTime.now().plusDays(30), 18);
	}
}