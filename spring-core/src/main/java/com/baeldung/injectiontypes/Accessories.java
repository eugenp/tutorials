package com.baeldung.injectiontypes;

import org.springframework.stereotype.Component;

@Component
public class Accessories {

	private String name;
	private Double amount;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
