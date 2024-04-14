package com.baeldung.evaluation.model.builder;

import org.junit.Test;

import com.baeldung.evaluation.model.Address;
import com.baeldung.evaluation.model.Person;


public class PersonCreatorTest {
	
	PersonCreator personCreator = new PersonCreator();

	@Test
    void testCloneInformation() throws CloneNotSupportedException {
		
		Person originalPerson = new Person();
		originalPerson.setAge(15);
		originalPerson.setAddress(new Address());
		originalPerson.getAddress().setCountry("Brazil");	
		
		// clone usage, change the existing value
		Person clonedPerson = (Person) originalPerson.clone();
		clonedPerson.getAddress().setCountry("New Zealand");	
		System.out.println(originalPerson.getAddress().getCountry()); // will print New Zealand
		
		// shallow copy, change the existing value
		Person shalowCopyPerson = personCreator.createShallowCopy(originalPerson);
		shalowCopyPerson.getAddress().setCountry("Australia");
		System.out.println(originalPerson.getAddress().getCountry()); // will print Australia
		
		//deep copy, do not change the existing value
		Person deepCopyPerson = (Person) personCreator.createDeepCopy(originalPerson);
		deepCopyPerson.getAddress().setCountry("Japan");	
		System.out.println(originalPerson.getAddress().getCountry()); // will print New Australia again
		
	}
}
