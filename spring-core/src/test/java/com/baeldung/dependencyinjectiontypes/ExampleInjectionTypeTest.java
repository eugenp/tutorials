package com.baeldung.dependencyinjectiontypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ExampleInjectionTypeTest {
    
    private static ApplicationContext context ;

    @Test
    public void whenConstructorInjection_thenSucessful() {
        context = new AnnotationConfigApplicationContext(ExampleConfig1.class);
        ExampleInjection exampleInjection = (ExampleInjection) context.getBean(ExampleInjection.class) ;
        
        assertEquals("Successfully injected from constructor",exampleInjection.getStrField());
        assertEquals(100, exampleInjection.getIntField());

        ((ConfigurableApplicationContext)context).close() ;
    }

    @Test
    public void whenSetterInjection_thenSucessful() {
        context = new AnnotationConfigApplicationContext(ExampleConfig2.class);
        ExampleInjection exampleInjection = (ExampleInjection) context.getBean(ExampleInjection.class) ;
        
        assertEquals("Successfully injected from setter",exampleInjection.getStrField());
        assertEquals(200, exampleInjection.getIntField());

        ((ConfigurableApplicationContext)context).close() ;
    }
}

@Configuration
class ExampleConfig1 {
    
    @Bean
    public ExampleInjection configure() {
        return new ExampleInjection("Successfully injected from constructor", 100) ;
    }
}

@Configuration
class ExampleConfig2 {
    
    @Bean
    public ExampleInjection configure() {
        ExampleInjection setterInjection = new ExampleInjection() ;
        setterInjection.setStrField("Successfully injected from setter");
        setterInjection.setIntField(200);
        return setterInjection ;
    }
}