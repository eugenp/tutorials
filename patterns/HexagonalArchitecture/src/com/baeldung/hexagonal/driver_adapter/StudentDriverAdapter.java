package com.baeldung.hexagonal.driver_adapter;

import java.util.List;

import com.baeldund.hexagonal.driver_port.StudentDriverPort;
import com.baeldung.hexagonal.core.Student;
import com.baeldung.hexagonal.core.StudentService;

public class StudentDriverAdapter implements StudentDriverPort {
	
	private StudentService studentService;
	
	public StudentDriverAdapter(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@Override
	public Student getStudent(int studentRollNo) {
		return studentService.getStudent(studentRollNo);
	}

	@Override
	public boolean addStudent(Student student) {
		return studentService.addStudent(student);
	}

	@Override
	public List<Student> getAllStudent() {
		return studentService.getAllStudent();
	}
	
}
