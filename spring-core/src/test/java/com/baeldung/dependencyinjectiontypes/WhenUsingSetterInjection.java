package com.baeldung.dependencyinjectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.configuration.DependencyInjectionSpring;
import com.baeldung.domain.Course;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DependencyInjectionSpring.class)
public class WhenUsingSetterInjection {

    @Autowired
    private Course course;

    @Test
    public void shouldReturnCourseName() {
        Assert.assertEquals("Physics", course.getName());
    }

    @Test
    public void shouldReturnStudentName() {
        Assert.assertEquals("Albert Einstein", course.getStudent()
            .getName());
    }
}