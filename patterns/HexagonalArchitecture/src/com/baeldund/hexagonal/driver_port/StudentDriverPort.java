package com.baeldund.hexagonal.driver_port;

import java.util.List;

import com.baeldung.hexagonal.core.Student;

public interface StudentDriverPort {
	
	public Student getStudent(int rollNo);
	
	public boolean addStudent(Student student);
	
	public List<Student> getAllStudent();
}
