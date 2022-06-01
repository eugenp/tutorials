package com.baeldung.multiple_bean_instantiation.solution3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier(value = "personOne, personTwo")
public class Person implements FactoryBean {
	private String firstName;
	private String secondName;

	public Person() {
		// initialization code (optional)
	}

	@Override
	public Class<Person> getObjectType() {
		return Person.class;
	}

	@Override
	public Object getObject() throws Exception {
		return new Person();
	}

	public boolean isSingleton() {
		return true;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", secondName=" + secondName + "]";
	}

}
