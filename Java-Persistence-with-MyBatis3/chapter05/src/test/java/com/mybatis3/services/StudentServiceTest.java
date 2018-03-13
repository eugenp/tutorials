package com.mybatis3.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mybatis3.domain.Address;
import com.mybatis3.domain.PhoneNumber;
import com.mybatis3.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class StudentServiceTest 
{
	@Autowired
	private StudentService studentService;
	
	@BeforeClass
	public static void setup() {
		TestDataPopulator.initDatabase();
	}
	
	@Test
	public void testFindAllStudents() {
		List<Student> students = studentService.findAllStudents();
		assertNotNull(students);
		for (Student student : students)
		{
			System.err.println(student);
		}
	}

	@Test
	public void testFindStudentById() {
		Student student = studentService.findStudentById(1);
		System.err.println(student);
		System.err.println(student.getAddress().getAddrId()+":"+student.getAddress().getCity());
		
	}

	@Test
	public void testFindStudentWithAddressById() {
		Student student = studentService.findStudentWithAddressById(2);
		assertNotNull(student);
		System.out.println(student.getAddress().getAddrId()+":"+student.getAddress().getCity());
	}

	@Test
	public void testCreateStudent() {
		//Address address = new Address();
		Address address = new Address(1,"Quaker Ridge Rd.","Bethel","Brooklyn","06801","USA");
		/*address.setStreet("Quaker Ridge Rd.");
		address.setCity("Bethel");
		address.setState("Brooklyn");
		address.setZip("06801");
		address.setCountry("USA");*/
		
		Student stud = new Student();
		long ts = System.currentTimeMillis();
		stud.setName("stud_"+ts);
		stud.setEmail("stud_"+ts+"@gmail.com");
		stud.setPhone(new PhoneNumber("123-456-7890"));
		stud.setAddress(address);
		Student student = studentService.createStudent(stud);
		assertNotNull(student);
		assertEquals("stud_"+ts, student.getName());
		assertEquals("stud_"+ts+"@gmail.com", student.getEmail());
		System.err.println("CreatedStudent: "+student);
	}

	@Test(expected=DataAccessException.class)
	public void testCreateStudentForException() {
		Address address = new Address();
		address.setStreet("Quaker Ridge Rd.");
		address.setCity("Bethel");
		address.setState("Brooklyn");
		address.setZip("06801");
		address.setCountry("USA");
		
		Student stud = new Student();
		long ts = System.currentTimeMillis();
		stud.setName("stud_"+ts);
		stud.setEmail("timothy@gmail.com");
		stud.setPhone(new PhoneNumber("123-456-7890"));
		stud.setAddress(address);
		studentService.createStudent(stud);
		fail("You should not reach here");
	}
	
	@Test
	public void testCreateStudentWithMap() {
		Map<String, Object> studMap = new HashMap<String, Object>();
		long ts = System.currentTimeMillis();
		studMap.put("name","stud_"+ts);
		studMap.put("email","stud_"+ts+"@gmail.com");
		studMap.put("phone",null);
		studentService.createStudentWithMap(studMap);
	}

	@Test
	public void testUpdateStudent() {
		Student stud = new Student();
		long ts = System.currentTimeMillis();
		stud.setStudId(2);
		stud.setName("studddd_"+ts);
		stud.setEmail("studddd_"+ts+"@gmail.com");
		Student student = studentService.updateStudent(stud);
		assertNotNull(student);
		assertEquals("studddd_"+ts, student.getName());
		assertEquals("studddd_"+ts+"@gmail.com", student.getEmail());
		assertEquals(new Integer(2), student.getStudId());
		
		System.out.println("UpdatedStudent: "+student);
	}

	@Test
	public void testDeleteStudent() {
		boolean deleted = studentService.deleteStudent(3);
		assertTrue(deleted);
		System.err.println("deleteStudent:"+deleted);
	}

	@Test
	public void testFindStudentMapById() {
		Map<String, Object> studentMap = studentService.findStudentMapById(1);
		System.err.println(studentMap);
	}

	@Test
	public void testFindAllStudentsMap() {
		List<Map<String,Object>> studentMapList = studentService.findAllStudentsMap();
    	for(Map<String,Object> studentMap : studentMapList)
    	{
    		System.out.println("id :"+studentMap.get("id"));
    		System.out.println("name :"+studentMap.get("name"));
    		System.out.println("email :"+studentMap.get("email"));
    		System.out.println("phone :"+studentMap.get("phone"));
    	}
	}

}
