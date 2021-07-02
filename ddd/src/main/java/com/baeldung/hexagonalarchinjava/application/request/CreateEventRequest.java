package com.baeldung.hexagonalarchinjava.application.request;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateEventRequest {
	@NotBlank
	private String eventName;

	@NotBlank
	private String organiser;

	@NotNull
	@Future
	private LocalDateTime eventTime;

	@NotBlank
	private String eventLocation;

	private int minAgeRequired;

	public String getEventName() {
		return eventName;
	}

	public String getOrganiser() {
		return organiser;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public int getMinAgeRequired() {
		return minAgeRequired;
	}

}
