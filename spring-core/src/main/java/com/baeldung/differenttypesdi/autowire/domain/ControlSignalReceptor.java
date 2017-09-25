package com.baeldung.differenttypesdi.autowire.domain;

import com.baeldung.differenttypesdi.shared.Processor;
import com.baeldung.differenttypesdi.shared.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControlSignalReceptor {

	@Autowired
	private Processor processor;

	public void receive(Signal signal) {
		if (signal == Signal.NEXT_CHANNEL) {
			this.processor.setNextChannel();
		} else if (signal == Signal.PREV_CHANNEL) {
			this.processor.setPrevChannel();
		}
	}

}
