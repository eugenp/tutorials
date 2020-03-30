package com.baeldung.hexagonal.main;

import com.baeldund.hexagonal.driver_port.StudentDriverPort;
import com.baeldung.hexagonal.core.Student;
import com.baeldung.hexagonal.core.StudentService;
import com.baeldung.hexagonal.driven_adapter.StudentRepositoryImpl;
import com.baeldung.hexagonal.driven_port.IStudentRepository;
import com.baeldung.hexagonal.driver_adapter.StudentDriverAdapter;

public class Main {

	public static void main(String[] args) {
		Student student = new Student(10, "Rafael", 9);
		
		IStudentRepository studentRepo = new StudentRepositoryImpl();
		StudentService studentService = new StudentService(studentRepo);
		StudentDriverPort studentInfo = new StudentDriverAdapter(studentService);
		
		// add a student
		boolean isStudentAdded = studentInfo.addStudent(student);
		
		if(isStudentAdded) {
			System.out.println("The student with enrollment no " + student.getRollNo() + " is successfully added");
		} else System.out.println("The student with enrollment no " + student.getRollNo() + " is already present");
		// get all students
		System.out.println("All the students are: ");
		studentInfo.getAllStudent().stream().forEach((x)->System.out.println(x));
		
		// get student with roll No 1	
		System.out.println("The student with roll no 1 is: ");
		System.out.println(studentInfo.getStudent(1));
		
	}

}
