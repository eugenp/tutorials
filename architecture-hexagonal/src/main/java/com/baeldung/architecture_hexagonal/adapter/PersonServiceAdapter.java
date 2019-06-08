package com.baeldung.architecture_hexagonal.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baeldung.architecture_hexagonal.core.Person;
import com.baeldung.architecture_hexagonal.port.PersonRepositoryPort;

@Service
public class PersonServiceAdapter implements PersonRepositoryPort {

	private static Map<Long, Person> phonebook = new HashMap<>();
	private static Long personId = new Long(1);

	@Override
	public void create(String name, String surname, String phoneNumber) {
		Person person = new Person();
		person.setId(personId);
		person.setName(name);
		person.setSurname(surname);
		person.setPhoneNumber(phoneNumber);
		phonebook.put(personId, person);
		personId = new Long(personId.intValue() + 1);
	}

	@Override
	public Person getPerson(Long id) {
		return phonebook.get(id);
	}

	@Override
	public List<Person> getPhonebook() {
		List<Person> phoneBookList = new ArrayList<>(phonebook.values());
		return phoneBookList;
	}

}
