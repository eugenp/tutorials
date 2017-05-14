package com.baeldung.spring;

public class Country {

    private State state;

	public Country(State state) {
	    System.out.println("Inside Country constructor." );
	    this.state = state;
	   }
	public void stateName() {
	    state.stateName();
	   }
}
