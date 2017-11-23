package com.baeldung.dependencyinjectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.configuration.DependencyInjectionSpring;
import com.baeldung.domain.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DependencyInjectionSpring.class)
public class WhenUsingConstructorInjection {

    @Autowired
    private Department department;

    @Test
    public void shouldReturnDepartmentName() {
        Assert.assertEquals("Technology", department.getName());
    }

    @Test
    public void shouldReturnEmployeeName() {
        Assert.assertEquals("Robert Langdon", department.getEmployee()
            .getName());
    }
}
