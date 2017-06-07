package com.baeldung;

public class Animal {
	private String type;
	
	// Constructor based injection example
	public Animal(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}

	// Uncomment for setter based injection example
	/**public void setType(String type) {
		this.type = type;
	}**/
	
	public void greet(){
		System.out.println("I am a " + getType());
	}
}
