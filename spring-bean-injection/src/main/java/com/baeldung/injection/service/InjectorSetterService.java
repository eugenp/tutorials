package com.baeldung.injection.service;

import org.springframework.stereotype.Service;

import com.baeldung.injection.beans.Person;

@Service
public class InjectorSetterService {

	private Person person;

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}
}
