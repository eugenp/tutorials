package com.baeldung.hexagonal.core;

import java.util.List;

import com.baeldung.hexagonal.driven_port.IStudentRepository;

public class StudentService {
	
	private IStudentRepository studentRepository;
	
	public StudentService(IStudentRepository studentRepo) {
		this.studentRepository = studentRepo;
	}
	
	public Student getStudent(int studentRollNo) {
		return studentRepository.getStudent(studentRollNo);
	}
	
	public List<Student> getAllStudent() {
		return studentRepository.getAllStudents();
	}
	
	public boolean addStudent(Student student) {
		return studentRepository.addStudent(student);
	}

}
