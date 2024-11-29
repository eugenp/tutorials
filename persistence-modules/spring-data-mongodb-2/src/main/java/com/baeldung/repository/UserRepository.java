package com.baeldung.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.baeldung.model.User;

public interface UserRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
}
