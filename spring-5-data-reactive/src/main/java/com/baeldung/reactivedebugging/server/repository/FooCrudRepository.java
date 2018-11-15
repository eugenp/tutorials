package com.baeldung.reactivedebugging.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.reactivedebugging.server.model.Foo;

@Repository
public interface FooCrudRepository extends ReactiveCrudRepository<Foo, Integer> {
}
