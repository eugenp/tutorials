package com.baeldung.differenttypesdi.autowire.domain;

public class RemoteControl implements Control {
	
	private Signal pressedButton;

	@Override
	public void pressButton(Signal signal) {
		this.pressedButton = signal;		
	}

	@Override
	public Signal emitSignal() {
		return pressedButton;
	}

}
