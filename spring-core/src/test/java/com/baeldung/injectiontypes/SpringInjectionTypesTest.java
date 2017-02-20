package com.baeldung.injectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.injectiontypes.domain.Student;
import com.baeldung.injectiontypes.service.StudentService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class SpringInjectionTypesTest {

    private final static String TRANSLATED = "TRANSLATED";

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