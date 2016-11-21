package com.baeldung.factorybean;

import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanInitializeTest {
    @Test(expected = BeanCreationException.class)
    public void testInitializationToolFactory() {
        new ClassPathXmlApplicationContext("classpath:factorybean-init-spring-ctx.xml");
    }

    @Test(expected = BeanCreationException.class)
    public void testPostConstructToolFactory() {
        new ClassPathXmlApplicationContext("classpath:factorybean-postconstruct-spring-ctx.xml");
    }
}
