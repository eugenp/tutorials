package com.baeldung.dependencyinjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmTwo {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmTwo.class);

	public String printTechnique() {
		return "AlgorithmTwo";
	}

}
