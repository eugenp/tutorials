package com.baeldung.injections;

import org.springframework.beans.factory.annotation.Autowired;

public class SampleClassUsingSettersForAutowiring {

	private CodeBuilder codeBuilder;

	@Autowired
	private void setCodeBuilder(CodeBuilder codeBuilder) {
		this.codeBuilder = codeBuilder;
	}
}
