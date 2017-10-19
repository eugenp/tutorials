package com.baeldung.setterditwo.domain;

public class Artist {
	
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	private int age;
	
	public Artist (String name, int age){
		this.name= name;
		this.age= age;
	}
	
	 @Override
	    public String toString() {
	        return String.format("%s %d", name, age);
	    }
	 
	 public Artist(){
		 
	 };

}
