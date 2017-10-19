package com.baeldung.setterditwo.domain;

public class Song {

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Song (String name){
		this.name = name;
	}
	
	public Song(){
		
	};
	
	@Override
	public String toString() {
		return String.format("%s", name);
	}
}