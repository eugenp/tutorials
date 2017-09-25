package com.baeldung.differenttypesdi.shared;

public interface Control {
	
	Signal emitSignal();
	
	void pressButton(Signal signal);

}
