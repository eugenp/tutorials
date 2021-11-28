package com.baeldung.hexagonalarch.service;

import com.baeldung.hexagonalarch.domain.model.Student;
import java.util.List;

public interface StudentService {

  Student addStudent(Student student);

  Student getStudent(Long studentId);

  List<Student> getAllStudents();

}
