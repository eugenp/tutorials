package com.baeldung.di.types;

import com.baeldung.di.types.annotation.School;
import com.baeldung.di.types.config.DIConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = DIConfig.class)
public class InjectionTypeTest {

    @Autowired
    private School school;

    @Test
    public void shouldWireDoctorAttributesUsingConstructor() {

        assertNotNull(school);
        assertNotNull(school.getSecurity());
        assertNotNull(school.getStudentService());
        assertNotNull(school.getTeacherService());
    }
}
