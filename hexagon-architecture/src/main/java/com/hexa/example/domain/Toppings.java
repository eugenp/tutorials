package com.hexa.example.domain;

public enum Toppings {
	MUSHROOM, OLIVE, TOMATO, CHEESE, HAM, PEPPERONI, CHICKEN, CORN;

	public String toString() {
		switch (this) {
		case MUSHROOM:
			return "Mushroom";
		case OLIVE:
			return "Olive";
		case TOMATO:
			return "Tomato";
		case CHEESE:
			return "Cheese";
		case HAM:
			return "Ham";
		case PEPPERONI:
			return "Pepperoni";
		case CHICKEN:
			return "Chicken";
		case CORN:
			return "Corn";
		}
		return null;
	}

}
