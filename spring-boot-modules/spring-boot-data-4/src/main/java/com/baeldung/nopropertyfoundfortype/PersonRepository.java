package com.baeldung.nopropertyfoundfortype;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByFirstName(String firstName);

	List<Person> findByLastName(String lastName);

	// Below same function will generate the error
	List<Person> findByLastname(String lastName);

}
