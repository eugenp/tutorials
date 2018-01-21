package com.baeldung.dependencyInjections;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.junit.Assert;

public class XmlConfigDITest {

    private ApplicationContext xmlConfigContext;

    @Before
    public void setup() {
        xmlConfigContext = new ClassPathXmlApplicationContext("spring_di_config.xml");
    }

    @Test
    public void testDependencyInjection() {
        ConstructorBasedInjection constructorBasedInjection1 = xmlConfigContext.getBean(ConstructorBasedInjection.class);
        ConstructorBasedInjection constructorBasedInjection2 = xmlConfigContext.getBean(ConstructorBasedInjection.class);
        Assert.assertNotNull(constructorBasedInjection1);
        Assert.assertNotNull(constructorBasedInjection2);
        Assert.assertEquals(constructorBasedInjection1, constructorBasedInjection2);
        SetterBasedInjection setterBasedInjection1 = xmlConfigContext.getBean(SetterBasedInjection.class);
        SetterBasedInjection setterBasedInjection2 = xmlConfigContext.getBean(SetterBasedInjection.class);
        Assert.assertNotNull(setterBasedInjection1);
        Assert.assertNotNull(setterBasedInjection2);
        Assert.assertEquals(setterBasedInjection1, setterBasedInjection2);
    }

}
