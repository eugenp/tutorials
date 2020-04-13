package poem.simple;

import poem.boundary.Boundary;
import poem.simple.driven_adapter.ConsoleWriter;
import poem.simple.driven_adapter.HardcodedPoemLibrary;
import poem.simple.driver_adapter.SimulatedUser;

/**
 * Main class that starts the hexagon example application.
 * 
 * The application is inspired by a talk by A. Cockburn and T. Pierrain on hexagonal architecture:
 * https://www.youtube.com/watch?v=th4AgBcrEHA
 * 
 *
 */
public class Main {
	public static void main(String[] args) {
		new Main().startApplication();
	}

	private void startApplication() {
		// Instantiate driven, right-side adapters
		HardcodedPoemLibrary poemLibrary = new HardcodedPoemLibrary();
		ConsoleWriter consoleWriter = new ConsoleWriter();

		// Inject driven adapters into boundary
		Boundary boundary = new Boundary(poemLibrary, consoleWriter);

		// Start the driver adapter for the application
		new SimulatedUser(boundary).run();
	}
}
