package com.baeldung.spring.data.couchbase.repos;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Student;

public interface CustomStudentRepository {
    List<Student> findByFirstNameStartsWith(String s);
}
