package com.mybatis3.services;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis3.domain.Student;



/**
 * @author Siva
 *
 */
public class StudentServiceTest 
{
	private static StudentService studentService;
	
	@BeforeClass
	public static void setup()
	{
		studentService = new StudentService();
		TestDataPopulator.initDatabase();
	}
	@AfterClass
	public static void teardown()
	{
		studentService = null;
	}
	
	@Test
    public void testFindAllStudents() 
	{
		List<Student> students = studentService.findAllStudents();
		assertNotNull(students);
		for (Student student : students)
		{
			assertNotNull(student);
			//System.out.println(student);
		}
		
	}
	
	@Test
    public void testFindStudentById() 
	{
		Student student = studentService.findStudentById(1);
		assertNotNull(student);
	}
	
	@Test
	public void testCreateUStudent() 
	{
		Student student = new Student();
		int id = 4;
		student.setStudId(id);
		student.setName("student_"+id);
		student.setEmail("student_"+id+"gmail.com");
		student.setDob(new Date());
		studentService.createStudent(student);
		Student newStudent = studentService.findStudentById(id);
		assertNotNull(newStudent);
		assertEquals("student_"+id, newStudent.getName());
		assertEquals("student_"+id+"gmail.com", newStudent.getEmail());
	}
	
	@Test	
	public void testUpdateStudent() 
	{
		int id = 2;
		Student student =studentService.findStudentById(id);
		student.setStudId(id);
		student.setName("student_"+id);
		student.setEmail("student_"+id+"gmail.com");
		Date now = new Date();
		student.setDob(now);
		studentService.updateStudent(student);
		Student updatedStudent = studentService.findStudentById(id);
		assertNotNull(updatedStudent);
		assertEquals("student_"+id, updatedStudent.getName());
		assertEquals("student_"+id+"gmail.com", updatedStudent.getEmail());
		
	}
}
