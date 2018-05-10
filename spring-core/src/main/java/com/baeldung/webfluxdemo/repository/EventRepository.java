package com.baeldung.webfluxdemo.repository;

import com.example.webfluxdemo.model.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface EventRepository extends ReactiveMongoRepository<Event, String> {
}
