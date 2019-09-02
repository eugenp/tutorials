package com.baeldung.hexagonalarch.external;

import java.io.Console;

public class ConsoleInterface {

	
	public  void createNewRent() {
		Console console = System.console();
		console.writer().print("Provide the new Rent Details:");
		// collect  the  rent data here using a System.console();
	}

}
