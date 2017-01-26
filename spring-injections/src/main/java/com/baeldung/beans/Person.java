package com.baeldung.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Person {
	
	@Autowired
	private Animal pet;
	
	private int age;
	
	@Value("Joseph")
	private String name;
	
	public Person() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Animal getPet() {
		return pet;
	}

	public void setPet(Animal pet) {
		this.pet = pet;
	}

	public int getAge() {
		return age;
	}
	
	@Required
	public void setAge(int age) {
		this.age = age;
	}
}
