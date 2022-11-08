package com.baeldung.nopropertyfoundfortype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoPropertyFoundForTypeApplicationUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonRepository repository;

	@Test
	public void givenPersonReposotiory_whenNoCorrectMapping_thenNoPropertyFoundForTypeError() {

		entityManager.persist(new Person("ABC", "DEF"));
		// Uncomment to get the error.
		// List<User> users = repository.findByLastname("Kumar");
		List<Person> persons = repository.findByLastName("DEF");
		assertEquals(1, persons.size());
		assertThat(persons).extracting(Person::getLastName).containsOnly("DEF");
	}
}
