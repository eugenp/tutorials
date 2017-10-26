package com.baeldung.autowired;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.baeldung.dependency.Student;
import com.baeldung.configuration.ApplicationContextTestBeanInjection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextTestBeanInjection.class)
public class StudentInjectionTest {

    @Autowired
    private Student student;

    @Test
    public void whenDependencyDefined_ThenDependencyInjected() {
        assertNotNull(student);
        assertNotNull(student.getGender());
        assertNotNull(student.getCourses());
    }

}
