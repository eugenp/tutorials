package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 import com.baeldung.model.Student;
import static org.junit.Assert.assertEquals;
 

public class XMLConfigSetterBasedInjectionIntregationTest {

 private static final int STUDENT_ID=2;
 private static final String STUDENT_NAME="Sam";
 private static final int SCHOOL_ID=2;
 private static final String SCHOOL_NAME="BUET";
public void givenXmlConfig_whenUsingSetterBasedInjection_thenCorrectStudentInfo(){
 ApplicationContext context = new ClassPathXmlApplicationContext("setter-context.xml");
 
         Student student = (Student) context.getBean("student");
	  assertEquals(STUDENT_ID, student.getId());
        assertEquals(STUDENT_NAME, student.getName());
        assertEquals(SCHOOL_ID, student.getSchool().getId());
        assertEquals(SCHOOL_NAME, student.getSchool().getName());
}

}
