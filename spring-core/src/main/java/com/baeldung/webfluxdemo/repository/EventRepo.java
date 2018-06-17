package com.baeldung.webfluxdemo.repository;


import com.baeldung.webfluxdemo.model.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface EventRepo extends ReactiveMongoRepository<Event, String> {
}
