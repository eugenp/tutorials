package com.baeldung.differenttypesdi.autowire.domain;

public interface Control {
	
	Signal emitSignal();
	
	void pressButton(Signal signal);

}
