package com.baeldung.dipractice;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
public class DiSetterBasedTest {

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    @Inject
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Test
    public void givenInjectAnnotation_WhenOnField_ThenValidDependency() {
        assertNotNull(employee);
    }
}
