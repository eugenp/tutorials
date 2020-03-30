package com.baeldung.hexagonal.driven_port;

import java.util.List;

import com.baeldung.hexagonal.core.Student;


public interface IStudentRepository {
	
	public Student getStudent(int rollNo);
	
	public boolean addStudent(Student student);
	
	public List<Student> getAllStudents();

}
