package com.springWebflux.SpringwebfluxeventStreaming.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springWebflux.SpringwebfluxeventStreaming.model.Student;

public interface StudentRepository extends ReactiveMongoRepository<Student, Integer>{

}
