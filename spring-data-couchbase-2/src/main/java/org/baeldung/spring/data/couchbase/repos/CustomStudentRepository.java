package org.baeldung.spring.data.couchbase.repos;

import java.util.List;

import org.baeldung.spring.data.couchbase.model.Student;

public interface CustomStudentRepository {
    List<Student> findByFirstNameStartsWith(String s);
}
