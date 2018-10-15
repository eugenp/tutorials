package com.baeldung.hexagonal.application.registration;

import static com.baeldung.hexagonal.application.registration.RegistrationStatus.BAD_REQUEST;
import static com.baeldung.hexagonal.application.registration.RegistrationStatus.OK;

import java.util.Objects;

public class RegistrationResponse {

	private RegistrationStatus status;
	private Long patientId;
	private String message;

	private RegistrationResponse(RegistrationStatus status, Long patientId, String message) {
		this.status = status;
		this.patientId = patientId;
		this.message = message;
	}

	public static RegistrationResponse ok(Long patientId) {
		return new RegistrationResponse(OK, patientId,
				String.format("Patient successfully registered with id: %s", patientId));
	}

	public static RegistrationResponse badRequest(String message) {
		return new RegistrationResponse(BAD_REQUEST, null, message);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RegistrationResponse response = (RegistrationResponse) o;
		return getStatus() == response.getStatus() && Objects.equals(getPatientId(), response.getPatientId())
				&& Objects.equals(getMessage(), response.getMessage());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStatus(), getPatientId(), getMessage());
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
		toStringBuilder.append("{ status=").append(status);
		toStringBuilder.append(", patientId=").append(patientId);
		toStringBuilder.append(", message='").append(message).append('\'');
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}

	public RegistrationStatus getStatus() {
		return status;
	}

	public Long getPatientId() {
		return patientId;
	}

	public String getMessage() {
		return message;
	}
}
