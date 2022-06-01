package com.baeldung.multiple_bean_instantiation.solution2;

import org.springframework.stereotype.Component;

import com.baeldung.multiple_bean_instantiation.solution2.Person;

/*@Component*/
public class PersonOne extends Person{

	public PersonOne(String firstName, String lastName) {
		super(firstName, lastName);
	}
}
