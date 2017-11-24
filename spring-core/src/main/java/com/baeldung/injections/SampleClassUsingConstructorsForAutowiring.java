package com.baeldung.injections;

import org.springframework.beans.factory.annotation.Autowired;

public class SampleClassUsingConstructorsForAutowiring {

	private CodeBuilder codeBuilder;

	@Autowired
	public SampleClassUsingConstructorsForAutowiring(CodeBuilder codeBuilder) {
		this.codeBuilder = codeBuilder;
	}
}
