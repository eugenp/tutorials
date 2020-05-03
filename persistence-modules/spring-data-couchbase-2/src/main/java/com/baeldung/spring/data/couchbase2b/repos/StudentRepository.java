package com.baeldung.spring.data.couchbase2b.repos;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {
    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);
}
