package com.baeldung.hexagonal.architecture.service.impl;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.domain.port.StudentRepository;
import com.baeldung.hexagonal.architecture.domain.port.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HexagonalArchitectureServiceImpl implements StudentService {
  @Autowired private StudentRepository studentRepository;

  @Override
  public Student saveStudentService(Student student) {
    return studentRepository.saveStudent(student);
  }

  @Override
  public Student getStudentService(long id) {
    return studentRepository.getStudent(id);
  }
}
