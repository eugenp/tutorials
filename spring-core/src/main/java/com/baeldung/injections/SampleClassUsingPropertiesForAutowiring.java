package com.baeldung.injections;

import org.springframework.beans.factory.annotation.Autowired;

class SampleClassUsingPropertiesForAutowiring {

    @Autowired
    private CodeBuilder codeBuilder;

	public CodeBuilder getCodeBuilder() {
		return codeBuilder;
	}

	public void setCodeBuilder(CodeBuilder codeBuilder) {
		this.codeBuilder = codeBuilder;
	}

}