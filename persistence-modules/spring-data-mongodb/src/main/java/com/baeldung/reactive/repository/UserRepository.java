package com.baeldung.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.baeldung.model.User;


public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
