package com.baeldung.hexagon;

import com.baeldung.hexagon.adapter.ConsoleOutput;
import com.baeldung.hexagon.adapter.Library;
import com.baeldung.hexagon.adapter.User;

public class Main {
	public static void main(String[] args) {
		new Main().startApplication();
	}

	private void startApplication() {
		// Instantiate adapters
		Library library = new Library();
		ConsoleOutput consoleOutput = new ConsoleOutput();

		// Inject adapters into boundary
		HexagonBoundary boundary = new HexagonBoundary(library, consoleOutput);

		// Start the driver adapter for the application
		new User(boundary).run();
	}
}
