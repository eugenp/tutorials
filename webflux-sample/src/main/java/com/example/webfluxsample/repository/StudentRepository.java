package com.example.webfluxsample.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.webfluxsample.model.Student;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {

}
