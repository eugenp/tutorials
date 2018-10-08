package com.baeldung.hexagonal;

public class HexagonalArchitecture {

	public interface Printer {
		void print(Cube cube);
	};

	public static class LengthPrinter implements Printer {
		public void print(Cube cube) {
			System.out.println("Cube of side length = " + cube.getLength());
		}
	}

	public static class VolumePrinter implements Printer {
		public void print(Cube cube) {
			double volume = Math.pow(cube.getLength(), 3);
			System.out.println("Cube of volume = " + volume);
		}
	}

	public static class Cube {
		private int sideLength;
		private Printer printer;

		public Cube() {
			printer = new LengthPrinter();	// Set the default printer
		}

		public int getLength() {
			return sideLength;
		}

		public void updateAndPrint(int newSideLength) {
			sideLength = newSideLength;
			printer.print(this);
		}

		public void updatePrinter(Printer newPrinter) {
			this.printer = newPrinter;
		}
	}

	public static void main(String[] args) {
		Cube c = new Cube();
		c.updateAndPrint(4);		// Prints "Cube of side length = 4"
		
		c.updatePrinter(new VolumePrinter());
		c.updateAndPrint(6);		// Prints "Cube of volume = 216.0"
	}

}
