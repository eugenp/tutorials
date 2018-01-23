package com.baeldung.difftypesdependencyinjection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.difftypesdependencyinjection.ClassConstructorInjection;
import com.baeldung.difftypesdependencyinjection.ClassSetterInjection;
import com.baeldung.difftypesdependencyinjection.Config;
import com.baeldung.difftypesdependencyinjection.Student;

/**
 * 
 * @author haseeb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class DiffTypesDependencyInjectionTest {

	@Autowired
	private Student student;
	
	@Autowired
	private ClassSetterInjection classSetterInjection;
	
	@Autowired
	private ClassConstructorInjection classConstructorInjection;
	
	@Test
	public void givenAutowiredAnnotation_whenSetOnSetter_thenStudentIsInjected() {
		
		student.setFirstName("Haseeb");
		student.setLastName("Ahmad");
		String studentName = classSetterInjection.getStudentName();
		assertTrue(studentName.equals(student.name()));
	} 
	
	@Test
	public void givenAutowiredAnnotation_whenSetOnConstructor_thenStudentIsInjected() {
		
		student.setFirstName("Haseeb");
		student.setLastName("Ahmad");
		String studentName = classConstructorInjection.getStudentName();
		assertTrue(studentName.equals(student.name()));
	} 
}
