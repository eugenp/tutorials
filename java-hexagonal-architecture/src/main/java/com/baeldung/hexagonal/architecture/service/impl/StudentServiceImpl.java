package com.baeldung.hexagonal.architecture.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.entity.Student;
import com.baeldung.hexagonal.architecture.repository.StudentRepository;
import com.baeldung.hexagonal.architecture.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void createStudent(Student student) {
        studentRepository.createStudent(student);
    }

    @Override
    public Optional<Student> getStudent(int id) {
        return studentRepository.getStudent(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

}
