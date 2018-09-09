package com.java.roundUp;


import java.util.Scanner;

public class RoundOff {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		double input = scanner.nextDouble();
		scanner.close();

		RoundOff off = new RoundOff();
		off.GenerateRoundNo(input);
	}

	int GenerateRoundNo(double input) {

		FuncInterface ref = (double d) -> {
			int i = (int) Math.round(d);
			return ((i + 99) / 100) * 100;
		};

		int number = ref.method(input);
		return number;
	}
}

@FunctionalInterface
interface FuncInterface {
	int method(double d);
}