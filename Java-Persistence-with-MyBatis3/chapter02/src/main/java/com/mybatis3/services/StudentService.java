package com.mybatis3.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis3.domain.Student;
import com.mybatis3.mappers.StudentMapper;


public class StudentService 
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SqlSessionFactory factory;
	public StudentService(SqlSessionFactory factory) {
		this.factory = factory;
	}
	
	protected SqlSession openSqlSession()
	{
		return factory.openSession();
	}
	public List<Student> findAllStudents()
	{
		SqlSession sqlSession = openSqlSession();
		try {
			//sqlSession.selectList("com.mybatis3.mappers.StudentMapper.findAllStudents");
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findAllStudents();
		} finally {
			sqlSession.close();
		}
	}
	
	public Student findStudentById(Integer studId)
	{
		logger.debug("Select Student By ID :{}", studId);
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findStudentById(studId);
		} finally {
			sqlSession.close();
		}
	}
	
	public Student createStudent(Student student)
	{
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.insertStudent(student);
			sqlSession.commit();
			return student;
		} finally {
			sqlSession.close();
		}
	}
	
	public void updateStudent(Student student)
	{
		SqlSession sqlSession = openSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.updateStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
