package com.baledung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baledung.student.Student;

@Controller
public class RestController{
    
	/**
	 * Get a student based on the id of the student
	 * specified in path variable {studentId}
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value="/student/{studentId}",method=RequestMethod.GET)
	public @ResponseBody Student getTestData(@PathVariable Integer studentId)
	{
		Student student = new Student();
		student.setName("Peter");
		student.setId(studentId);
		
		return student;
		
	}
}
