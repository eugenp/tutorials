package com.baeldung.injectiontypes;

import org.springframework.stereotype.Component;

@Component
public class Warranty {

	private String name;
	private Integer term;
	private String type;
	
	public Warranty(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getTerm() {
		return term;
	}
	
	public void setTerm(Integer term) {
		this.term = term;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
