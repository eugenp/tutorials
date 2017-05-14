package com.baeldung.beaninjection;

public class SayGreeting {

	private GreetInterface greet;

	public SayGreeting(GreetInterface greet) {
		this.greet = greet;
	}

	public SayGreeting() {
	}

	public void setGreet(GreetInterface greet) {
		this.greet = greet;
	}

	public void doHelloWorld() {
		System.out.println(greet.getGreeting() + " World!");
	}
}
