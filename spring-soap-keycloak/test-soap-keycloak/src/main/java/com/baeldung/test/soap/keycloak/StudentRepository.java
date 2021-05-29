package com.baeldung.test.soap.keycloak;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baeldung.test.soap.keycloak.gen.Student;



@Component
public class StudentRepository {
	private static final Map<String, Student> students = new HashMap<>();

	@PostConstruct
	public void initData() {
		
		Student student = new Student();
		student.setName("John");
		student.setStandard(5);
		student.setAddress("New York");
		students.put(student.getName(), student);
		
		student = new Student();
		student.setName("Anna");
		student.setStandard(5);
		student.setAddress("Chicago");
		students.put(student.getName(), student);
		
		student = new Student();
		student.setName("Helmut");
		student.setStandard(6);
		student.setAddress("Berlin");
		students.put(student.getName(), student);
		
		student = new Student();
		student.setName("Claudia");
		student.setStandard(7);
		student.setAddress("Rome");
		students.put(student.getName(), student);
		
		
	}

	public Student findStudent(String name) {
		Assert.notNull(name, "The Student's name must not be null");
		return students.get(name);
	}
}