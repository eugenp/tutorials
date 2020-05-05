package com.hexagonalArcht.demo.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonalArcht.demo.model.Student;
import com.hexagonalArcht.demo.ports.StudentUIPort;

@RestController
@RequestMapping("/student/")
public class StudentControllerAdapter implements StudentUIPort {
@Autowired
private StudentServiceAdapter studentService;

@Override
public void register(@RequestBody Student request) {
studentService.register(request.getName());
}

@Override
public Student view(@PathVariable Integer id) {
Student student = studentService.getStudent(id);
return student;
}
}