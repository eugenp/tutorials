package com.baeldung.spring.di.constructor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Deal {
	private double price;
	private double volume;
}
