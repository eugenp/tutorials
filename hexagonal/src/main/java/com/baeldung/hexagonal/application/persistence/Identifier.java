package com.baeldung.hexagonal.application.persistence;

import java.util.Objects;

class Identifier {

	private static final String DEFAULT_SYSTEM = "default";

	private String system;
	private String value;

	private Identifier(String system, String value) {
		this.system = system;
		this.value = value;
	}

	static Identifier from(String value) {
		return new Identifier(DEFAULT_SYSTEM, value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Identifier that = (Identifier) o;
		return Objects.equals(getSystem(), that.getSystem()) && Objects.equals(getValue(), that.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSystem(), getValue());
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder = new StringBuilder(getClass().getSimpleName());
		toStringBuilder.append("{ system='").append(system).append('\'');
		toStringBuilder.append(", value='").append(value).append('\'');
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}

	public String getSystem() {
		return system;
	}

	public String getValue() {
		return value;
	}
}
