package com.baeldung.setterdi.dhaval;

public class Lesson {
	private String message1;

	public Lesson() {
		System.out.println("Inside Default constructor of Dependency class.");
	}

	public void setMessage1(String message1) {
		System.out.println(message1 + " from Dependency class.");
	}

	public void startLearning() {
		System.out.println("We are learning Setter DI.");
	}
}
