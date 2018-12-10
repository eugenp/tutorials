package com.baeldung.hexagonalarchitecture;
import java.util.stream.Collectors;

public class ConsoleDisplay implements DisplaySource {

	@Override
	public void print(Result result) {
		String strList = result.numbers.stream().map(n -> String.valueOf(n))
			      .collect(Collectors.joining(", ", "[", "]"));
		System.out.println(String.format("Input numbers: %s", strList));
		System.out.println(String.format("Average of numbers: %.3f", result.average));
	}

}
