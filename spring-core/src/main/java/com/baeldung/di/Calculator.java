package com.baeldung.di;

public class Calculator {

	private Addition addition;

    public Calculator(Addition addition) {
		System.out.println("Inside Calculator constructor.");
		this.addition = addition;
	}

	public int calculateAddition(int a, int b) {
		return addition.calculateAddition(a, b);
	}
	
	
}