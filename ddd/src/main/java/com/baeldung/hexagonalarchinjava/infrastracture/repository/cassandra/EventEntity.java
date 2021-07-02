package com.baeldung.hexagonalarchinjava.infrastracture.repository.cassandra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("event_entity")
public class EventEntity {
	
	@PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
	private UUID id;

	@Column("event_name")
	private String eventName;

	@Column("event_time")
	private LocalDateTime eventTime;

	@Column("event_status")
	private String eventStatus;

	@Column("event_location")
	private String eventLocation;
	
	@Column("organiser")
	private String organiser;

	@Column("participants")
	private List<ParticipantEntity> participants;
	
	@Column("min_age")
	private int minAgeRestriction;

	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	
	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public List<ParticipantEntity> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantEntity> participants) {
		this.participants = participants;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public int getMinAgeRestriction() {
		return minAgeRestriction;
	}

	public void setMinAgeRestriction(int minAgeRestriction) {
		this.minAgeRestriction = minAgeRestriction;
	}

	public String getOrganiser() {
		return organiser;
	}

	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}
	
}
