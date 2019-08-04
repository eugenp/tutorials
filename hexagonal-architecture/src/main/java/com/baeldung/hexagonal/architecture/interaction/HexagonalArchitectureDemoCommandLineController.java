package com.baeldung.hexagonal.architecture.interaction;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.domain.port.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HexagonalArchitectureDemoCommandLineController {

  @Autowired private StudentService studentService;

  public Student getStudent(String id) {
    Student student = studentService.getStudentService(Long.parseLong(id));
    return student;
  }

  public Student createStudent(Map<String, String> request) {
    Student student = new Student();
    student.setAge(Integer.parseInt(request.get("age")));
    student.setName(request.get("name"));

    student = studentService.saveStudentService(student);

    return student;
  }
}
