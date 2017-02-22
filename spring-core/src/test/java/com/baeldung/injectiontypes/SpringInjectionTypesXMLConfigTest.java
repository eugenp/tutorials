package com.baeldung.injectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.baeldung.injectiontypes.domain.Student;
import com.baeldung.injectiontypes.xmlconfig.service.StudentService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class SpringInjectionTypesXMLConfigTest {


    @Autowired
    private StudentService serviceInjectDependencyByConstrucot;

    @Autowired
    private StudentService serviceInjectDependencyBySetter;
   

    @Test
    public void apologizeWithTranslatedMessage() {
    	Student student = new Student("Alex", 25);
		Student student2 = new Student("David", 26);

		serviceInjectDependencyByConstrucot.addStudent(student);
		serviceInjectDependencyBySetter.addStudent(student2);
    	System.out.println("testing done");
    }

}