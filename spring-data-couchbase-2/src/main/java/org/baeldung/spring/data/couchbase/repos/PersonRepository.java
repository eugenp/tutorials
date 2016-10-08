package org.baeldung.spring.data.couchbase.repos;

import java.util.List;

import org.baeldung.spring.data.couchbase.model.Person;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {
	@View
    List<Person> findByFirstName(String firstName);
	@View
    List<Person> findByLastName(String lastName);
}
