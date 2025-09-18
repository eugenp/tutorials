package com.baeldung.jacksonlazyfields.dao;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jacksonlazyfields.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
