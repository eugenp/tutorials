package com.baeldung.reactive.webflux;

import java.util.Date;

public class EventData {
	public String eventId;
	public Date when;

	public EventData() {
	}

	public EventData(String event, Date when) {
		super();
		this.eventId = event;
		this.when = when;
	}
}