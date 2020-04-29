package com.baeldung.spring.data.couchbase.repos;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String>, CustomStudentRepository {
    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);
}
