package com.baeldung.hexagon.adapter;

import com.baeldung.hexagon.internal.AskBook;
import com.baeldung.hexagon.port.ICommand;

/**
 * The invocation adapter.
 * 
 */
public class User {
	private ICommand driverPort;

	public User(ICommand driverPort) {
		this.driverPort = driverPort;
	}

	public void run() {
		driverPort.reactTo(new AskBook("charles dickens"));
	}
}
