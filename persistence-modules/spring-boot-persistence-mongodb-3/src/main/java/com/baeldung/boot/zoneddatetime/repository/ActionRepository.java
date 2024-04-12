package com.baeldung.boot.zoneddatetime.repository;


import com.baeldung.boot.zoneddatetime.model.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionRepository extends MongoRepository<Action, String> { }