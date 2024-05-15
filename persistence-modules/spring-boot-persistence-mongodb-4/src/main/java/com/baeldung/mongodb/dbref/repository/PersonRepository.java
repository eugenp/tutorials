package com.baeldung.mongodb.dbref.repository;

import com.baeldung.mongodb.dbref.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
