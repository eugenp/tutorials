package com.baeldung.di.setter.model.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Computer {

	private Processor processor;
	private Memory memory;

	public Processor getProcessor() {
		return processor;
	}
	
	@Autowired
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public Memory getMemory() {
		return memory;
	}
	
	@Autowired
	public void setMemory(Memory memory) {
		this.memory = memory;
	}

}
