package com.baeldung.injections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SampleClassWithBuilders {

	@Autowired
	@Qualifier("javaBuilder")
	private CodeBuilder javaBuilder;

	@Autowired
	@Qualifier("pythonBuilder")
	private CodeBuilder pythonBuilder;

}
