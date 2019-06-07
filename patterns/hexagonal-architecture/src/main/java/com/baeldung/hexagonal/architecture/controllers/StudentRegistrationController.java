package com.baeldung.hexagonal.architecture.controllers;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.exception.NotFoundException;
import com.baeldung.hexagonal.architecture.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(value = "/student")
public class StudentRegistrationController extends BaseController{

	@Autowired
	@Qualifier("studentDbAdapter")
	StudentPort studentPort;

	@RequestMapping(value= "/register", consumes = "application/json", produces = "application/json",method = POST)
    public ResponseEntity<String> registerStudent(@RequestBody Student student) throws BadRequestException
    {
    	studentPort.registerStudent(student);
    	return new ResponseEntity<>("Student Registration Successful!", HttpStatus.OK);
    }

	@RequestMapping(value="/{registrationNo}",produces = "application/json",method = GET)
	public ResponseEntity<Student> viewRegistration(@PathVariable("registrationNo") Integer registrationNo) throws NotFoundException{
		Student student = studentPort.viewRegistration(registrationNo);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

}
