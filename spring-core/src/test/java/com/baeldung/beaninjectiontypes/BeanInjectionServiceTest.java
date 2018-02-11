package com.baeldung.beaninjectiontypes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    loader = AnnotationConfigContextLoader.class,
    classes = BeanConfiguration.class)
public class BeanInjectionServiceTest {

    @Test
    public void whenConstructorInjection_thenAssertEquals () {

        AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(BeanConfiguration.class);

        ConstructorBeanInjectionService constructorInjectionService
            = context.getBean(ConstructorBeanInjectionService.class);

        String toBeTested = "Hello";

        Assert.assertTrue(toBeTested.equals(constructorInjectionService.readMessage()));
    }

    @Test
    public void whenSetterInjection_thenAssertEquals () {

        AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(BeanConfiguration.class);

        SetterBeanInjectionService setterInjectionService
            = context.getBean(SetterBeanInjectionService.class);

        String toBeTested = "Hello";

        Assert.assertTrue(toBeTested.equals(setterInjectionService.readMessage()));
    }

    @Test
    public void whenPropertyInjection_thenAssertEquals () {

        AbstractApplicationContext context
            = new AnnotationConfigApplicationContext(BeanConfiguration.class);

        PropertyBeanInjectionService propertyInjectionService
            = context.getBean(PropertyBeanInjectionService.class);

        String toBeTested = "Hello";

        Assert.assertTrue(toBeTested.equals(propertyInjectionService.readMessage()));
    }
}
