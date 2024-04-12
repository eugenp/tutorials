package com.baeldung.boot.dbref.repository;

import com.baeldung.boot.dbref.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
