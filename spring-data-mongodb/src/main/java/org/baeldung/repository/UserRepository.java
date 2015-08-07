package org.baeldung.repository;

import java.util.List;

import org.baeldung.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends MongoRepository<User, String>, QueryDslPredicateExecutor<User> {
    @Query("{ 'name' : ?0 }")
    List<User> findUsersByName(String name);

    @Query(value = "{'age':?0}", fields = "{ 'name' : 1}")
    List<String> findUsersByAgeAndReturnNames(int age);

    List<User> findByAgeBetween(int ageGT, int ageLT);
}
