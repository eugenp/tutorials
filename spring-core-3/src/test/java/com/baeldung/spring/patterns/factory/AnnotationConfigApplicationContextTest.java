package com.baeldung.spring.patterns.factory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationConfigApplicationContextTest {

    @Test
    @SuppressWarnings("resource")
    public void whenGetSimpleBean_thenReturnConstructedBean() {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        
        Foo foo = context.getBean(Foo.class);
        
        assertNotNull(foo);
    }

    @Test
    @SuppressWarnings("resource")
    public void whenGetPrototypeBean_thenReturnConstructedBean() {
        
        String expectedName = "Some name";
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        
        Bar bar = context.getBean(Bar.class, expectedName);
        
        assertNotNull(bar);
        assertThat(bar.getName(), is(expectedName));
    }
    
    @Test
    @SuppressWarnings("resource")
    public void givenXmlConfiguration_whenGetSimpleBean_thenReturnConstructedBean() {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("patterns-context.xml");
        
        Foo foo = context.getBean(Foo.class);
        
        assertNotNull(foo);
    }

    @Test
    @SuppressWarnings("resource")
    public void givenXmlConfiguration_whenGetPrototypeBean_thenReturnConstructedBean() {
        
        String expectedName = "Some name";
        ApplicationContext context = new ClassPathXmlApplicationContext("patterns-context.xml");
        
        Bar bar = context.getBean(Bar.class, expectedName);
        
        assertNotNull(bar);
        assertThat(bar.getName(), is(expectedName));
    }
}
