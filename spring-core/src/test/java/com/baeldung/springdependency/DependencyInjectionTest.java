package com.baeldung.springdependency;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTest {

    @Test
    public void whenSetterDependencyInjection_CreateBeanInstance() {
        
        AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        
        TutorialService ts = container.getBean(TutorialSetterImpl.class);
        String actualResult = ts.getTutorial().getName();
        String expectedResult = "Spring Tutorial";
        
        assertEquals(expectedResult, actualResult);
        
        container.close();
    }
    
    @Test
    public void whenConstructorDependencyInjection_CreateBeanInstance() {
        
        AnnotationConfigApplicationContext container = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        
        TutorialService ts = container.getBean(TutorialConstructorImpl.class);
        String actualResult = ts.getTutorial().getName();
        String expectedResult = "Spring Tutorial";
        
        assertEquals(expectedResult, actualResult);
        
        container.close();
    }

}
