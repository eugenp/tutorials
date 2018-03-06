package com.mybatis3.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis3.domain.Student;
import com.mybatis3.mappers.StudentMapper;
import com.mybatis3.util.MyBatisSqlSessionFactory;


/**
 * @author Siva
 *
 */
public class StudentService 
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<Student> findAllStudents()
	{
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findAllStudents();
		} finally {
			sqlSession.close();
		}
	}
	
	public Student findStudentById(Integer studId)
	{
		logger.debug("Select Student By ID :{}", studId);
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			return studentMapper.findStudentById(studId);
			//return sqlSession.selectOne("com.mybatis3.StudentMapper.findStudentById", studId);
		} finally {
			sqlSession.close();
		}
	}
	
	public void createStudent(Student student)
	{
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.insertStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
	
	public void updateStudent(Student student)
	{
		SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
		try {
			StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
			studentMapper.updateStudent(student);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
