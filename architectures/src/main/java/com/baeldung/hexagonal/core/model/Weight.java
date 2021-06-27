package com.baeldung.hexagonal.core.model;

public class Weight {
	float weight;

	Weight(float weight) {
		// We allow bigger weights because the user might be tracking something other than her/his weight.
		if (weight < 0) throw new IllegalArgumentException("Weight must be bigger than 0.0kg.");

		this.weight = weight;
	}

	@Override
	public String toString() {
		return String.valueOf(weight);
	}
}
