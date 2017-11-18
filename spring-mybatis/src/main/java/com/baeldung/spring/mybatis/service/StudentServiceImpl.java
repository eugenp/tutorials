package com.baeldung.spring.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.mybatis.mappers.StudentMapper;
import com.baeldung.spring.mybatis.model.Student;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Transactional
	public boolean insertStudent(Student student) {
		boolean result=false;
		try{
			studentMapper.insertStudent(student);
			result = true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	public Student getStudentByLogin(String userName, String password) {
		Student student = studentMapper.getStudentByUserName(userName);
		return student;
	}

	public Student getStudentByUserName(String userName) {
		Student student = studentMapper.getStudentByUserName(userName);
		return student;
	}

}
