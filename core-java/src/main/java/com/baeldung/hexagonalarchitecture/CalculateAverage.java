package com.baeldung.hexagonalarchitecture;

public class CalculateAverage {
	private InputSource input;
	private DisplaySource display;
	private Result result;
	public CalculateAverage(InputSource input, DisplaySource display) {
		this.input = input;
		this.display = display;
	}
	public void calculate() {
		InputData data = input.getData();
		double average = data.numbers.stream().mapToInt(Integer::intValue).average().getAsDouble();
		result = new Result();
		result.average = average;
		result.numbers = data.numbers;
	}
	public void printResult() {
		display.print(result);
	}
}
