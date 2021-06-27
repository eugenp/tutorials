package com.baeldung.hexagonal.core.model;

import java.time.LocalDateTime;

public class WeightInTime {
	long id;
	Weight weight;
	LocalDateTime when;

	public WeightInTime(long id, float weightInKg, LocalDateTime when) {
		this(new Weight(weightInKg), when);
		this.id = id;
	}

	public WeightInTime(Weight weight, LocalDateTime when) {
		this.weight = weight;
		this.when = when;
	}
	
	public Weight getWeight() {
		return weight;
	}
	
	public LocalDateTime getWhen() {
		return when;
	}

	public long getId() {
		return id;
	}
}
