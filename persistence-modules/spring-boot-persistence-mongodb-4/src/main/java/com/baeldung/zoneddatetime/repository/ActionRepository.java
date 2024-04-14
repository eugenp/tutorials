package com.baeldung.zoneddatetime.repository;

import com.baeldung.zoneddatetime.model.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionRepository extends MongoRepository<Action, String> { }