package com.baeldung.hexagonalarchitecture.application;

import com.baeldung.hexagonalarchitecture.application.boundary.Boundary;
import com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter.GreetingsDictionary;
import com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter.GreetingsPrinter;
import com.baeldung.hexagonalarchitecture.infrastructutre.driveradapter.GreetingsMachine;

public class AppRunner {
        public static void main(String[] args) {
                // Instantiate driven, right-side adapters
                GreetingsDictionary greetingsDictionary = new GreetingsDictionary();
                GreetingsPrinter greetingsPrinter = new GreetingsPrinter();

                // Inject driven adapters into boundary
                Boundary boundary = new Boundary(greetingsDictionary, greetingsPrinter);

                // Start the driver adapter for the application
                new GreetingsMachine(boundary).run();
        }

}
