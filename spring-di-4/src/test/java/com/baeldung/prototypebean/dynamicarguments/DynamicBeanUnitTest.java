package com.baeldung.prototypebean.dynamicarguments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = EmployeeConfig.class)
public class DynamicBeanUnitTest {

    @Test
    public void givenPrototypeBean_WhenFunction_ThenNewInstanceReturn() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(EmployeeConfig.class);
        EmployeeBeanUsingFunction firstContext = context.getBean(EmployeeBeanUsingFunction.class);
        EmployeeBeanUsingFunction secondContext = context.getBean(EmployeeBeanUsingFunction.class);
        Employee firstInstance = firstContext.getEmployee("sachin");
        Employee secondInstance = secondContext.getEmployee("kumar");
        Assert.assertTrue(firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeBean_WhenLookup_ThenNewInstanceReturn() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(EmployeeConfig.class);
        EmployeeBeanUsingLookUp firstContext = context.getBean(EmployeeBeanUsingLookUp.class);
        EmployeeBeanUsingLookUp secondContext = context.getBean(EmployeeBeanUsingLookUp.class);
        Employee firstInstance = firstContext.getEmployee("sachin");
        Employee secondInstance = secondContext.getEmployee("kumar");
        Assert.assertTrue(firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeBean_WhenObjectProvider_ThenNewInstanceReturn() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(EmployeeConfig.class);
        EmployeeBeanUsingObjectProvider firstContext = context.getBean(EmployeeBeanUsingObjectProvider.class);
        EmployeeBeanUsingObjectProvider secondContext = context.getBean(EmployeeBeanUsingObjectProvider.class);
        Employee firstInstance = firstContext.getEmployee("sachin");
        Employee secondInstance = secondContext.getEmployee("kumar");
        Assert.assertTrue(firstInstance != secondInstance);
    }
}
