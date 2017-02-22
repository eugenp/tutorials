package com.baeldung.injectiontypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.baeldung.injectiontypes.domain.Student;
import com.baeldung.injectiontypes.javaconfig.InjectionTypesConfig;
import com.baeldung.injectiontypes.javaconfig.service.StudentService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InjectionTypesConfig.class)
public class SpringInjectionTypesJavaConfigTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void apologizeWithTranslatedMessage() {

        Student student = new Student("David", 26);
        studentService.addStudent(student);
        System.out.println("testing done");
    }

}