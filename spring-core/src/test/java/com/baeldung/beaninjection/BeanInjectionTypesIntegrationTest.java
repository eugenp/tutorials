package com.baeldung.beaninjection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BeanInjectionTypesIntegrationTest {

    private ApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
    private ApplicationContext xmlConfigContext = new ClassPathXmlApplicationContext("beaninjection.xml");

    @Test
    public void givenAnnotationConfig_whenConstructorInjectionUsed_thenDependencyIsInjected() {
        ConstructorBasedInjection constructorBasedInjection = annotationConfigContext.getBean(ConstructorBasedInjection.class);

        assertNotNull(constructorBasedInjection);
        assertNotNull(constructorBasedInjection.getDependencyBean());
        assertEquals("MyDependency", constructorBasedInjection.getDependencyBean()
            .getName());
    }

    @Test
    public void givenAnnotationConfig_whenSetterInjectionUsed_thenDependencyIsInjected() {
        SetterBasedInjection setterBasedInjection = annotationConfigContext.getBean(SetterBasedInjection.class);

        assertNotNull(setterBasedInjection);
        assertNotNull(setterBasedInjection.getDependencyBean());
        assertEquals("MyDependency", setterBasedInjection.getDependencyBean()
            .getName());
    }

    @Test
    public void givenXmlConfig_whenConstructorInjectionUsed_thenDependencyIsInjected() {
        ConstructorBasedInjection constructorBasedInjection = xmlConfigContext.getBean(ConstructorBasedInjection.class);

        assertNotNull(constructorBasedInjection);
        assertNotNull(constructorBasedInjection.getDependencyBean());
        assertEquals("MyDependency", constructorBasedInjection.getDependencyBean()
            .getName());
    }

    @Test
    public void givenXmlConfig_whenSetterInjectionUsed_thenDependencyIsInjected() {
        SetterBasedInjection setterBasedInjection = xmlConfigContext.getBean(SetterBasedInjection.class);

        assertNotNull(setterBasedInjection);
        assertNotNull(setterBasedInjection.getDependencyBean());
        assertEquals("MyDependency", setterBasedInjection.getDependencyBean()
            .getName());
    }

}