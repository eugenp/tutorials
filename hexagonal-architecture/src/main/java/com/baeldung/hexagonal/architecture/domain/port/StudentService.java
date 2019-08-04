package com.baeldung.hexagonal.architecture.domain.port;

import com.baeldung.hexagonal.architecture.domain.Student;

public interface StudentService {
  Student saveStudentService(Student student);

  Student getStudentService(long id);
}
