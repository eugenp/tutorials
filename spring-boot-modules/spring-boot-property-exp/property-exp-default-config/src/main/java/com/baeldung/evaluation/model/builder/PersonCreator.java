package com.baeldung.evaluation.model.builder;

import com.baeldung.evaluation.model.Address;
import com.baeldung.evaluation.model.Person;

public class PersonCreator {
	
	public Person createShallowCopy (Person existingPerson) {
		
		Person newPerson = new Person();
		newPerson.setAge(existingPerson.getAge());
		newPerson.setAddress(existingPerson.getAddress());
		return newPerson;
		
	}

	public Person createDeepCopy(Person existingPerson) {
		
		Person newPerson = new Person();
		newPerson.setAge(existingPerson.getAge());
		newPerson.setAddress(new Address());
		newPerson.getAddress().setStreet(existingPerson.getAddress().getStreet());
		newPerson.getAddress().setCountry(existingPerson.getAddress().getCountry());
		return newPerson;
	}
	
}

