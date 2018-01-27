package com.baeldung.dependencyinjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmOne {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmOne.class);

	public String printTechnique() {
		return "AlgorithmOne";
	}
}
