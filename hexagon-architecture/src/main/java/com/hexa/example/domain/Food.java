package com.hexa.example.domain;

public enum Food {

	PIZZA, BURGER, BREAD, PINANI;

	public String toString() {
		switch (this) {
		case PIZZA:
			return "Pizza";
		case BURGER:
			return "Burger";
		case BREAD:
			return "Bread";
		case PINANI:
			return "Pinani";
		}
		return null;
	}

}
