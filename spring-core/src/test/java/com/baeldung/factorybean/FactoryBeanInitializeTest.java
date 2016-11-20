package com.baeldung.factorybean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanInitializeTest {
    @Test(expected = Exception.class)
    public void testInitializationToolFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean-init-spring-ctx.xml");
    }

    @Test(expected = Exception.class)
    public void testPostConstructToolFactory() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:factorybean-postconstruct-spring-ctx.xml");
    }
}
