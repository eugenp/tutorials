package org.baeldung.service.ports;

import org.baeldung.persistence.entity.Student;
import org.baeldung.persistence.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IStudentDb {

    Student create(Student student);
    Optional<Student> findStudentById(Long id);
}
