package com.baeldung.dependencyinjection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Execute {

	private static final Logger LOGGER = LoggerFactory.getLogger(Execute.class);

	@Autowired
	private AlgorithmOne one;

	private AlgorithmTwo two;

	@Autowired
	public void setAlgorithmTwo(AlgorithmTwo two) {
		this.two = two;
	}

	public Execute(AlgorithmOne one) {
		this.one = one;
	}

	public void printAlgorithms() {
		LOGGER.info("Technique ::" + one.printTechnique());
		LOGGER.info("Technique ::" + two.printTechnique());
	}

}
