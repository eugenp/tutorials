package com.baeldung.student.service.dbimpl;

import com.baeldung.student.service.StudentService;
import com.baeldung.student.model.Student;

public class StudentDbService implements StudentService{

	public String create(Student student){
		System.out.println("Creating student in DB...");
		return student.registrationId;
	}

	public Student read(String registrationId){
		System.out.println("Reading student from DB...");
		return new Student();
	}

	public Student update(Student student){
		System.out.println("Updating sutdent in DB...");
		return student;
	}

	public String delete(String registrationId){
		System.out.println("Deleteing sutdent in DB...");
		return registrationId;
	}
}