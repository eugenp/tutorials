package com.baeldung.reactivedebugging.consumer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.reactivedebugging.consumer.model.FooDao;

@Repository
public interface FooCrudRepository extends ReactiveCrudRepository<FooDao, Integer> {
}
