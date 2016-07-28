package com.baledung.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baledung.student.Student;

@RestController
public class RestAnnotatedController 
{   
	@RequestMapping(value="/annotated/student/{studentId}")
	public Student getData(@PathVariable Integer studentId)
	{
		Student student = new Student();
		student.setName("Peter");
		student.setId(studentId);
		
		return student;
	}
}
