package com.baeldung.patterns.data;

public class Strategies {
	public record Ones() implements Strategy {
	}

	public record Twos() implements Strategy {
	}

	public record OnePair() implements Strategy {
	}

	public record TwoPairs() implements Strategy {
	}

	public record ThreeOfaKind() implements Strategy {
	}

	public static Strategy fromString(String strategyString) {
		return switch (strategyString) {
			case "ONES" -> new Ones();
			case "TWOS" -> new Twos();
			case "ONE_PAIR" -> new OnePair();
			case "TWO_PAIRS" -> new TwoPairs();
			case "THREE_OF_A_KIND" -> new ThreeOfaKind();
			default -> throw new IllegalStateException("Unknown strategy: " + strategyString);
		};
	}
}
