package com.baeldung.spring.patterns.factory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationConfigApplicationContextTest {

    @Test
    @SuppressWarnings("resource")
    public void whenGetSimpleBean_ThenReturnConstructedBean() {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        
        Foo foo = context.getBean(Foo.class);
        
        assertNotNull(foo);
    }

    @Test
    @SuppressWarnings("resource")
    public void whenGetPrototypeBean_ThenReturnConstructedBean() {
        
        String expectedName = "Some name";
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        
        Bar bar = context.getBean(Bar.class, expectedName);
        
        assertNotNull(bar);
        assertThat(bar.getName(), is(expectedName));
    }
}
