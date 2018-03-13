package com.mybatis3.mappers;

import java.util.List;

import com.mybatis3.domain.Student;


/**
 * @author Siva
 *
 */
public interface StudentMapper
{

	List<Student> findAllStudents();

	Student findStudentById(Integer id);

	void insertStudent(Student student);

	void updateStudent(Student student);

}
