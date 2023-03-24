package com.baeldung.circulardependency;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    @Autowired
    ApplicationContext context;

    @Bean
    public CircularDependencyA getCircularDependencyA() {
        return new CircularDependencyA();
    }

    @Bean
    public CircularDependencyB getCircularDependencyB() {
        return new CircularDependencyB();
    }

    @Test
    public void givenCircularDependency_whenSetterInjection_thenItWorks() {
        final CircularDependencyA circA = context.getBean(CircularDependencyA.class);

        Assert.assertEquals("Hi!", circA.getCircB().getMessage());
    }
    
    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() {
        // Empty test; we just want the context to load
    }
}
