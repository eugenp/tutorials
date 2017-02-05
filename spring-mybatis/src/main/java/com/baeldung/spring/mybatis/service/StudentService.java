package com.baeldung.spring.mybatis.service;

import com.baeldung.spring.mybatis.model.Student;

public interface StudentService {
	void insertStudent(Student student);
	boolean getStudentByLogin(String userName, String password);
	boolean getStudentByUserName(String userName);
}