package com.baeldung.hexagonal.architecture.infrastructure.impl;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.domain.port.StudentRepository;

public class StudentRepositoryInH2Database implements StudentRepository {
  @Override
  public Student saveStudent(Student student) {
    return null;
  }

  @Override
  public Student getStudent(long id) {
    return null;
  }
}
