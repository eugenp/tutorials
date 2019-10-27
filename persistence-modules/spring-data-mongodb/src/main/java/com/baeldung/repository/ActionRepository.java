package com.baeldung.repository;

import com.baeldung.model.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionRepository extends MongoRepository<Action, String> { }