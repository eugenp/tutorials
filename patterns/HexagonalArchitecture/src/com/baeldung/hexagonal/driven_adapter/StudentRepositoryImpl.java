package com.baeldung.hexagonal.driven_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baeldung.hexagonal.core.Student;
import com.baeldung.hexagonal.driven_port.IStudentRepository;


public class StudentRepositoryImpl implements IStudentRepository {
	
	private Map<Integer, Student> studentMap = new HashMap<>();
	
	public StudentRepositoryImpl() {
		initializeStudentMap();
	}
	
	private void initializeStudentMap() {
		studentMap.put(1, new Student(1,"Jane", 12));
		studentMap.put(2, new Student(2,"Jacob", 11));
		studentMap.put(3, new Student(3,"Mike", 11));
	}	

	@Override
	public Student getStudent(int rollNo) {
		Student student = Optional.ofNullable(studentMap.get(rollNo)).orElse(null);
		return student;
	}

	@Override
	public boolean addStudent(Student student){
		int studentRollNo = student.getRollNo();
		if (studentMap.containsKey(studentRollNo)) {
			return false;
		}
		studentMap.put(studentRollNo, student);
		return true;
	}
	
	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = new ArrayList<>();
		studentMap.forEach((key, value) -> studentList.add(value));
		return studentList;	
	}

}
