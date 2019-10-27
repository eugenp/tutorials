package org.baeldung.port.outbound;

import org.baeldung.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IRepositoryPort {
    Student create(Student student);
    Optional<Student> findStudentById(Long id);
    List<Student> getAll();
}
