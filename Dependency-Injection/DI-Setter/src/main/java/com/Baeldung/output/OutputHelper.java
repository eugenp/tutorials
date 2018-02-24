package com.Baeldung.output;

import com.Baeldung.output.IBaseService;

public class OutputHelper {
	IBaseService outputGenerator;

	public void generateOutput() {
		outputGenerator.generateOutput();
	}

	//DI via setter method
	public void setOutputGenerator(IBaseService outputGenerator) {
		this.outputGenerator = outputGenerator;
	}
}