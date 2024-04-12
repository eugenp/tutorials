package com.baeldung.mongodb.dbref.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.mongodb.dbref.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

}
