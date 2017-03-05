package com.baeldung.di.setter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.configuration.ApplicationContextTestSetterDI;

public class SetterDIInjectionTest {
    
    private static ApplicationContext context;
    
    @BeforeClass
    public static void initializeContext() {
        context = new AnnotationConfigApplicationContext(ApplicationContextTestSetterDI.class);
    }
    
    @Test
    public void givenConstructorBasedDI_WhenCalledSerivce_ShouldUseInjectedBeanMethod() {
        SetterInjectedDIGreeter greeter = context.getBean(SetterInjectedDIGreeter.class);
        Assert.assertNotNull(greeter.getGreetingService());
        String outMessage = greeter.greet("Tom");
        Assert.assertNotNull(outMessage);
        Assert.assertEquals("Hello Tom", outMessage);
    }
}
