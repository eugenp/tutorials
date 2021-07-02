package com.baeldung.hexagonalarchinjava.application.request;

import javax.validation.constraints.NotNull;

import com.baeldung.hexagonalarchinjava.domain.Participant;

public class ParticipantRequest {

	@NotNull
	private Participant participant;

	public Participant getParticipant() {
		return participant;
	}
}
