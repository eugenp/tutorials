package com.baeldung.hexagonalarchitecture;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleInput implements InputSource {

	@Override
	public InputData getData() {
		InputData data = new InputData();
		ArrayList<Integer> numbers = new ArrayList();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextInt()) {
			numbers.add(scanner.nextInt());
		}
		data.numbers = numbers;
		return data;
	}

}
