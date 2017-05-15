package com.baeldung.injection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.injection.beans.Person;

@Service
public class InjectConstructorService {

	private Person person;

	@Autowired
	public InjectConstructorService(Person personBean) {
		this.person = personBean;
	}

	public Person getPerson() {
		return person;
	}
}
