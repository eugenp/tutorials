package com.baeldung.ditypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTest {

    @Test
    public void testDependencyInjection() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        ConstructorHelloWorld constructorHelloWorld = context.getBean(ConstructorHelloWorld.class);
        SetterHelloWorld setterHelloWorld = context.getBean(SetterHelloWorld.class);
        FieldHelloWorld fieldHelloWorld = context.getBean(FieldHelloWorld.class);

        assertEquals("Hello World!", constructorHelloWorld.getHelloWorldBean());
        assertEquals("Hello World!", setterHelloWorld.getHelloWorldBean());
        assertEquals("Hello World!", fieldHelloWorld.getHelloWorldBean());
    }
}
