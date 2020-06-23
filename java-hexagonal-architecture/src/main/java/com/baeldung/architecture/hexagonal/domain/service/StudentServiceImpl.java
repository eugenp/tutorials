package com.baeldung.architecture.hexagonal.domain.service;

import com.baeldung.architecture.hexagonal.domain.Student;
import com.baeldung.architecture.hexagonal.domain.exceptions.StudentNotFoundException;
import com.baeldung.architecture.hexagonal.domain.repository.StudentRepository;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long enrolStudent(Student student) {
        student = studentRepository.add(student);
        return student.getId();
    }

    @Override
    public void unregisterStudent(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(StudentNotFoundException::new);
        studentRepository.deleteById(student.getId());
    }

    @Override
    public String evaluatePerformance(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(StudentNotFoundException::new);
        student.evaluatePerformance();
        studentRepository.update(student);
        return student.getPerformanceRating();
    }
}
