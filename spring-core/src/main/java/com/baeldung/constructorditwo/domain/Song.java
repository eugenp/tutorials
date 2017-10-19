package com.baeldung.constructorditwo.domain;

public class Song {

	private String name;
	
	public Song (String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("%s", name);
	}
}
