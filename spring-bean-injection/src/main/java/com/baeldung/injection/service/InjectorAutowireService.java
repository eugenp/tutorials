package com.baeldung.injection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.injection.beans.Person;

@Service
public class InjectorAutowireService {

	@Autowired
	private Person person;

	public Person getPerson() {
		return person;
	}
}
