package com.baeldung.hexagonal.application.persistence;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PersistedPatient {

	private Long id;
	private Set<Identifier> identifiers;
	private List<String> givenNames;
	private String familyName;

	private PersistedPatient(Long id, Set<Identifier> identifiers, List<String> givenNames, String familyName) {
		this.id = id;
		this.identifiers = identifiers;
		this.givenNames = givenNames;
		this.familyName = familyName;
	}

	public static PersistedPatient from(Long id, TransientPatient patient) {
		return new PersistedPatient(id, patient.getIdentifiers(), patient.getGivenNames(), patient.getFamilyName());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		PersistedPatient patient = (PersistedPatient) object;
		return Objects.equals(getId(), patient.getId())
				&& Objects.equals(getIdentifiers(), patient.getIdentifiers())
				&& Objects.equals(getGivenNames(), patient.getGivenNames())
				&& Objects.equals(getFamilyName(), patient.getFamilyName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getIdentifiers(), getGivenNames(), getFamilyName());
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
		toStringBuilder.append("{ id=").append(id);
		toStringBuilder.append(", identifiers=").append(identifiers);
		toStringBuilder.append(", givenNames=").append(givenNames);
		toStringBuilder.append(", familyName='").append(familyName).append('\'');
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}

	public Long getId() {
		return id;
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
