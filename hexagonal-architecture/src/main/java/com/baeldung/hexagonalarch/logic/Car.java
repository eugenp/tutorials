package com.baeldung.hexagonalarch.logic;

import java.io.Serializable;

public class Car implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String make;
	
	private boolean available;
	
	
	public Car(){}
	
	public Car(String make,boolean available){
		this.make=make;
		this.available=available;
	}
	
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	

}
	  