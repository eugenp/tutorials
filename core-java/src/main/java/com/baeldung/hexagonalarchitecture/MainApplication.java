package com.baeldung.hexagonalarchitecture;

public class MainApplication {
	public static void main(String[] args) {
		InputSource input = new ConsoleInput();
		DisplaySource display = new ConsoleDisplay();
		CalculateAverage calculateAverage = new CalculateAverage(input, display);
		calculateAverage.calculate();
		calculateAverage.printResult();
	}
}
