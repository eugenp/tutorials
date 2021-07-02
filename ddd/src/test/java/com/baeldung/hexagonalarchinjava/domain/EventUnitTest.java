package com.baeldung.hexagonalarchinjava.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventUnitTest {

	@Test
	void shouldCancelEvent_thenChangeEventStatus() {
		final Event event = this.createFutureTestEvent();

		event.cancelEvent();

		assertEquals(EventStatus.CANCELLED, event.getStatus());
	}

	@Test
	void shouldCompleteEvent_thenChangeStatus() {
		final Event event = this.createFutureTestEvent();

		event.completeEvent();

		assertEquals(EventStatus.COMPLETED, event.getStatus());
	}

	@Test
	void shouldAddParticipant_thenUpdateEvent() {
		final Event event = this.createFutureTestEvent();

		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(20);

		event.addParticipant(part);

		assertEquals(true, event.getParticipants().contains(part));
	}

	@Test
	void shouldRemoveParticipant_thenUpdatePrice() {
		final Event event = this.createFutureTestEvent();

		Participant part = new Participant();
		part.setId(UUID.randomUUID());
		part.setName("test-name");
		part.setEmail("test@email.com");
		part.setContactNumber(10000000l);
		part.setAge(20);

		event.addParticipant(part);

		event.completeEvent();

		final Executable executable = () -> event.removeParticipant(part);

		Assertions.assertThrows(EventServiceException.class, executable);
	}

	private Event createFutureTestEvent() {
		return new Event(UUID.randomUUID(), "test", "test-user", "test-location", LocalDateTime.now().plusDays(30), 18);
	}
}