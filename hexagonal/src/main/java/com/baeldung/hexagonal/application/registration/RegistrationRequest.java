package com.baeldung.hexagonal.application.registration;

import java.util.List;
import java.util.Objects;

public class RegistrationRequest {

	private String identifier;
	private List<String> givenNames;
	private String familyName;

	private RegistrationRequest(String identifier, List<String> givenNames, String familyName) {
		this.identifier = identifier;
		this.givenNames = givenNames;
		this.familyName = familyName;
	}

	public static RegistrationRequest from(String identifier, List<String> givenNames, String familyName) {
		return new RegistrationRequest(identifier, givenNames, familyName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RegistrationRequest request = (RegistrationRequest) o;
		return Objects.equals(getIdentifier(), request.getIdentifier())
				&& Objects.equals(getGivenNames(), request.getGivenNames())
				&& Objects.equals(getFamilyName(), request.getFamilyName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdentifier(), getGivenNames(), getFamilyName());
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
		toStringBuilder.append("{ identifier='").append(identifier).append('\'');
		toStringBuilder.append(", givenNames=").append(givenNames);
		toStringBuilder.append(", familyName='").append(familyName).append('\'');
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}

	public String getIdentifier() {
		return identifier;
	}

	public List<String> getGivenNames() {
		return givenNames;
	}

	public String getFamilyName() {
		return familyName;
	}
}
