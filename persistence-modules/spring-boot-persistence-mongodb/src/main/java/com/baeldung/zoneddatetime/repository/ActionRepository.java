package com.baeldung.zoneddatetime.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.zoneddatetime.model.Action;

public interface ActionRepository extends MongoRepository<Action, String> { }