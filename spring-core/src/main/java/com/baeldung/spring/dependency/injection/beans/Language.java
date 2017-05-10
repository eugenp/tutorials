package com.baeldung.spring.dependency.injection.beans;

public class Language {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void printLanguageName(){
		System.out.println("Language : "+name);
	}

}
