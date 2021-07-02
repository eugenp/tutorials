package com.baeldung.hexagonalarchinjava.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Event {

	private UUID id;
	private String eventName;
	private String organiser;
	private String eventLocation;
	private LocalDateTime eventTime;
	private EventStatus status;
	private int minAgeRestriction;
	private Set<Participant> participants;

	public Event() {
		super();
	}

	public Event(final UUID id, String eventName, String organiser, String eventLocation, LocalDateTime eventTime,
			int ageRestriction) {
		this.id = id;
		this.eventName = eventName;
		this.organiser = organiser;
		this.eventLocation = eventLocation;
		this.eventTime = eventTime;
		this.status = EventStatus.CREATED;
		this.minAgeRestriction = ageRestriction;
		this.participants = new HashSet<>();
	}

	public void addParticipant(Participant participant) {

		this.validateAge(participant);

		this.validateState();

		this.participants.add(participant);
	}

	public void removeParticipant(Participant participant) {

		this.validateState();

		this.participants.remove(participant);
	}

	public void cancelEvent() {
		this.validateState();
		this.status = EventStatus.CANCELLED;
	}

	public void completeEvent() {
		this.validateState();
		this.status = EventStatus.COMPLETED;
	}

	private void validateState() {
		if (this.status.equals(EventStatus.CANCELLED) || this.status.equals(EventStatus.COMPLETED)) {
			throw new EventServiceException("Event cannot changed");
		}
	}

	private void validateAge(Participant participant) {

		if (this.minAgeRestriction > participant.getAge()) {
			throw new EventServiceException("Participant not allowed due to age restriction");
		}
	}

	public UUID getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public EventStatus getStatus() {
		return status;
	}

	public int getMinAgeRestriction() {
		return minAgeRestriction;
	}

	public Set<Participant> getParticipants() {
		return participants;
	}

	public String getOrganiser() {
		return organiser;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

}
