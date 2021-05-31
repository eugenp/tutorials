package com.baeldung;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentService")
public class StudentController {
	@Autowired
	private StudentService service;

	@GetMapping
	public ResponseEntity<List<StudentEntity>> getAllStudents() {
		List<StudentEntity> studentList = service.getAllStudents();
		return new ResponseEntity<List<StudentEntity>>(studentList, HttpStatus.OK);
	}
}
