package com.baeldung.reactive.model;

public class Event {
	
	private Long eventId;
	private String eventMessage;
	
	public Event() {
		super();
	}
	
	public Event(Long eventId, String eventMessage) {
		super();
		this.eventId = eventId;
		this.eventMessage = eventMessage;
	}
	
	public Long getEventId() {
		return eventId;
	}
	
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String getEventMessage() {
		return eventMessage;
	}
	
	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventMessage=" + eventMessage + "]";
	}
}
