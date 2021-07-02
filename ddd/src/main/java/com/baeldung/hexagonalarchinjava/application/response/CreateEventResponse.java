package com.baeldung.hexagonalarchinjava.application.response;

import java.util.UUID;

public class CreateEventResponse {

	private UUID id;

	public CreateEventResponse(final UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
}
