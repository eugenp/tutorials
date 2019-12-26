package org.baeldung.spring.data.couchbase2b.repos;

import java.util.List;

import org.baeldung.spring.data.couchbase.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {
    List<Person> findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);
}
