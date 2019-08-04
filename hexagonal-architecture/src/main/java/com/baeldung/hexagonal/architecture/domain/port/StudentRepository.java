package com.baeldung.hexagonal.architecture.domain.port;

import com.baeldung.hexagonal.architecture.domain.Student;

public interface StudentRepository {
  Student saveStudent(Student student);

  Student getStudent(long id);
}
