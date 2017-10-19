package com.baeldung.constructorditwo.domain;

public class Artist {
	
	private String name;
	private int age;
	
	public Artist (String name, int age){
		this.name= name;
		this.age= age;
	}
	
	 @Override
	    public String toString() {
	        return String.format("%s %d", name, age);
	    }

}
