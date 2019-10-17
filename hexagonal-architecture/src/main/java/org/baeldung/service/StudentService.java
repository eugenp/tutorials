package org.baeldung.service;

import org.baeldung.persistence.entity.Student;
import org.baeldung.persistence.repository.StudentRepository;
import org.baeldung.service.ports.IStudentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements IStudentDb {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
