package com.baeldung.factorybean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryBeanInitializeTest {
    @Test(expected = Exception.class)
    public void testInitializationToolFactory() {
        new ClassPathXmlApplicationContext("classpath:factorybean-init-spring-ctx.xml");
    }

    @Test(expected = Exception.class)
    public void testPostConstructToolFactory() {
        new ClassPathXmlApplicationContext("classpath:factorybean-postconstruct-spring-ctx.xml");
    }
}
