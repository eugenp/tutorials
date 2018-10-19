package com.baeldung.spring.mybatis.service;

import com.baeldung.spring.mybatis.model.Student;

public interface StudentService {
	public boolean insertStudent(Student student);
	public Student getStudentByLogin(String userName, String password);
	public Student getStudentByUserName(String userName);
}