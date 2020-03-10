package com.technopreneur.patterns.core.domain;

import java.io.Serializable;

public class Pizza implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String[] toppings;

	public Pizza(String name) {
		super();
		this.name = name;
	}
	
	public Pizza(String name, String[] toppings) {
		super();
		this.name = name;
		this.toppings = toppings;
	}

	public String getName() {
		return name;
	}

	public String[] getToppings() {
		return toppings;
	}

	public void setToppings(String[] toppings) {
		this.toppings = toppings;
	}
	
	
	
}
