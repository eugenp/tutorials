package com.baeldung.architecture_hexagonal.port;

import java.util.List;

import com.baeldung.architecture_hexagonal.core.Person;

public interface PersonRepositoryPort {

	public void create(String name, String surname, String phoneNumber);

	public Person getPerson(Long id);

	public List<Person> getPhonebook();

}
