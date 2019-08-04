package com.baeldung.hexagonal.architecture.interaction;

import com.baeldung.hexagonal.architecture.domain.Student;
import com.baeldung.hexagonal.architecture.domain.port.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class HexagonalArchitectureDemoController {

  private final Logger logger = LoggerFactory.getLogger(HexagonalArchitectureDemoController.class);

  @Autowired private StudentService studentService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<Student> getStudent(@PathVariable String id) {
    Student student = studentService.getStudentService(Long.parseLong(id));
    return new ResponseEntity(student, HttpStatus.OK);
  }

  @PostMapping(path = "/")
  public ResponseEntity<Student> createStudent(@RequestBody HashMap<String, String> request) {
    Student student = new Student();
    student.setAge(Integer.parseInt(request.get("age")));
    student.setName(request.get("name"));

    student = studentService.saveStudentService(student);

    return new ResponseEntity(student, HttpStatus.CREATED);
  }
}
