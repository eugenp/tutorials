package com.baeldung.di.setter.model.bean;

public class Computer {

	private Processor processor;
	private Memory memory;

	public Processor getProcessor() {
		return processor;
	}
	
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public Memory getMemory() {
		return memory;
	}
	
	public void setMemory(Memory memory) {
		this.memory = memory;
	}

}
