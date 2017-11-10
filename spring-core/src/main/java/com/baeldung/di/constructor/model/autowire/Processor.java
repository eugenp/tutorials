package com.baeldung.di.constructor.model.autowire;

import org.springframework.stereotype.Component;

@Component
public class Processor {

	private Integer cores = 2;
	private Double frequency = 1.4;

	public Integer getCores() {
		return cores;
	}

	public Double getFrequency() {
		return frequency;
	}

}
