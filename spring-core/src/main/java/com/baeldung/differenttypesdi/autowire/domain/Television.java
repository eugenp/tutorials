package com.baeldung.differenttypesdi.autowire.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Television {

	@Autowired
	private Processor processor;
	
	@Autowired
	private ControlSignalReceptor controlSignal;

	public Television(Processor processor) {
		this.processor = processor;
	}

	public Integer showCurrentChannel() {
		return processor.getCurrentChannel();
	}
	
	public void receiveSignal(Signal signal) {
		controlSignal.receive(signal);
	}

}
