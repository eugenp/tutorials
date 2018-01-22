package com.baeldung.dependencyinjectiontypes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class InjectionTest
{

    private AnnotationConfigApplicationContext ctx;

    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(MyInjectedBean.class, ConstructorInjection.class, SetterInjection.class);
    }

    @Test
    public void testConstructorInjection() {
        ConstructorInjection constructorInjection = ctx.getBean(ConstructorInjection.class);
        Assert.assertNotNull(constructorInjection.getMyInjectedBean());
    }

    @Test
    public void testSetterInjection() {
        SetterInjection setterInjection = ctx.getBean(SetterInjection.class);
        Assert.assertNotNull(setterInjection.getMyInjectedBean());
    }

    @Test
    public void testSetterInjectionNonMandatory() {
        SetterInjection setterInjection = new SetterInjection();
        Assert.assertNull(setterInjection.getMyInjectedBean());
    }
}
