package com.baeldung.injectiontypes.xmlconfig.service;

import com.baeldung.injectiontypes.dao.StudentDAO;
import com.baeldung.injectiontypes.domain.Student;

public class StudentService{

	StudentDAO dao;
	
	public StudentService() {
	}

	public StudentService(StudentDAO dao){
		this.dao = dao;
	}

	public void addStudent(Student std) {
		dao.insert(std);
	}

	public void setDao(StudentDAO dao) {
		this.dao = dao;
	}

}
