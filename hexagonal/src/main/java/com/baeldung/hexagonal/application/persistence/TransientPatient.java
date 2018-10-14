package com.baeldung.hexagonal.application.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TransientPatient {

	private Set<Identifier> identifiers;
	private List<String> givenNames;
	private String familyName;

	private TransientPatient(Set<Identifier> identifiers, List<String> givenNames, String familyName) {
		this.identifiers = identifiers;
		this.givenNames = givenNames;
		this.familyName = familyName;
	}

	public static TransientPatient from(String identifier, List<String> givenNames, String familyName) {
		return new TransientPatient(Collections.singleton(Identifier.from(identifier)), givenNames, familyName);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		TransientPatient patient = (TransientPatient) object;
		return Objects.equals(getIdentifiers(), patient.getIdentifiers())
				&& Objects.equals(getGivenNames(), patient.getGivenNames())
				&& Objects.equals(getFamilyName(), patient.getFamilyName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdentifiers(), getGivenNames(), getFamilyName());
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
		toStringBuilder.append("{ identifiers=").append(identifiers);
		toStringBuilder.append(", givenNames=").append(givenNames);
		toStringBuilder.append(", familyName='").append(familyName).append('\'');
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}

	public Set<Identifier> getIdentifiers() {
		return identifiers;
	}

	public List<String> getGivenNames() {
		return givenNames;
	}

	public String getFamilyName() {
		return familyName;
	}
}
