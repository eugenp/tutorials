package com.baeldung.injectiontypes.dao;

import com.baeldung.injectiontypes.domain.Student;

public class StudentDAO {

	public StudentDAO() {
	}

	public void insert(Student student) {
		System.out.println("Student with name: " + student.getName()
				+ " will be inserted to DB");
		// code here to connect and insert to DB
	}

}
