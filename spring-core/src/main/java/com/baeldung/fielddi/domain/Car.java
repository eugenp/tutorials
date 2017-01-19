package com.baeldung.fielddi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
	@Autowired
	private Engine engine;
	@Autowired
	private Transmission transmission;

	@Override
	public String toString() {
		return String.format("Engine: %s Transmission: %s", engine, transmission);
	}
}
