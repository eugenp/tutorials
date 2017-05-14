package com.baeldung.di.constructor;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.configuration.ApplicationContextTestConsturctorDI;

public class ConstructorDIInjectionTest {
    
    private static ApplicationContext context;
    
    @BeforeClass
    public static void initializeContext() {
        context = new AnnotationConfigApplicationContext(ApplicationContextTestConsturctorDI.class);
    }
    
    @Test
    public void givenConstructorBasedDI_WhenCalledSerivce_ShouldUseInjectedBeanMethod() {
        ConstructorInjectedDIGreeter greeter = context.getBean(ConstructorInjectedDIGreeter.class);
        String outMessage = greeter.greet("Tom");
        Assert.assertNotNull(outMessage);
        Assert.assertEquals("Hello Tom", outMessage);
    }
}
