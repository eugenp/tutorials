package com.baeldung.beaninjection;

import com.baeldung.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class XMLConfigConstructorInjectionIntegrationTest {

	private static final int STUDENT_ID=1;
	 private static final String STUDENT_NAME="Peter";
	 private static final int SCHOOL_ID=1;
	 private static final String SCHOOL_NAME="Massachusetts Institute of Technology";
	
void givenXmlonfig_whenUsingConstructorInjection_thenCorrectStudentInfo(){
ApplicationContext context = new ClassPathXmlApplicationContext("constructor-context.xml");
Student student = (Student) context.getBean("student");
assertEquals(STUDENT_ID, student.getId());
         assertEquals(STUDENT_NAME, student.getName());
         assertEquals(SCHOOL_ID, student.getSchool().getId());
         assertEquals(SCHOOL_NAME, student.getSchool().getName());
}

}
