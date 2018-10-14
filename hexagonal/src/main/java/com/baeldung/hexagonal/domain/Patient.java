package com.baeldung.hexagonal.domain;

import java.util.List;
import java.util.Objects;

public class Patient {

	private String identifier;
	private List<String> givenNames;
	private String familyName;

	private Patient(String identifier, List<String> givenNames, String familyName) {
		validateIdentifiers(identifier);
		validateGivenNames(givenNames);
		validateFamilyName(familyName);
		this.identifier = identifier;
		this.givenNames = givenNames;
		this.familyName = familyName;
	}

	public static Patient from(String identifier, List<String> givenNames, String familyName) {
		return new Patient(identifier, givenNames, familyName);
	}

	private void validateIdentifiers(String identifier) {
		if (identifier == null || identifier.isEmpty()) {
			throw InvalidIdentifierException.causedBy("Identifier invalid: Identifiers may not be null or empty");
		}
	}

	private void validateGivenNames(List<String> givenNames) {
		if (givenNames == null || givenNames.isEmpty()) {
			throw EmptyNamesException.causedBy("No given names provided");
		}
		for (int i = 0; i < givenNames.size(); i++) {
			String name = givenNames.get(i);
			if (name == null || name.isEmpty()) {
				throw InvalidNameException.causedBy(
						String.format("Given name at index %s invalid: Name may not be null or empty", i));
			}
		}
	}

	private void validateFamilyName(String familyName) {
		if (familyName == null || familyName.isEmpty()) {
			throw InvalidNameException.causedBy("Family name invalid: Name may not be null or empty");
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Patient patient = (Patient) object;
		return Objects.equals(getIdentifier(), patient.getIdentifier()) && Objects.equals(getGivenNames(),
				patient.getGivenNames()) && Objects.equals(getFamilyName(), patient.getFamilyName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdentifier(), getGivenNames(), getFamilyName());
	}

	@Override
	public String toString() {
		final StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
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
