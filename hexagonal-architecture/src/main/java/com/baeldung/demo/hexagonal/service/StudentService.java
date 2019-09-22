package com.baeldung.demo.hexagonal.service;

import com.baeldung.demo.hexagonal.model.Student;
import com.baeldung.demo.hexagonal.repository.StudentRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepositoryPort studentRepository;

    public void create(String name) {
        studentRepository.create(name);
    }

    public Student getStudent(Integer studentId) {
        return studentRepository.getStudent(studentId);
    }

}
