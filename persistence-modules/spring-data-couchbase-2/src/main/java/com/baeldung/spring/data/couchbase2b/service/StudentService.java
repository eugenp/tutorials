package com.baeldung.spring.data.couchbase2b.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.spring.data.couchbase.model.Student;

public interface StudentService {

    Optional<Student> findOne(String id);

    List<Student> findAll();

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    void create(Student student);

    void update(Student student);

    void delete(Student student);
}
