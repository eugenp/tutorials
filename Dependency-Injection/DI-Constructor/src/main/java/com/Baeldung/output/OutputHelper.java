package com.Baeldung.output;

import com.Baeldung.output.IBaseService;

public class OutputHelper {
	IBaseService outputGenerator;

	public void generateOutput() {
		outputGenerator.generateOutput();
	}

	//DI via constructor
	public OutputHelper(IBaseService outputGenerator){
		this.outputGenerator = outputGenerator;
	}
	
}