package com.baeldung.dependencyinjectiontypes;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DependencyInjectionTest {

	private ApplicationContext context;
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
	}
	
    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
        
    	RunnerWithSetterInjection runnerWithSetterInjection = (RunnerWithSetterInjection) context.getBean("runnerWithSetterInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedText = runnerWithSetterInjection.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedText));
    }
    
    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
    	
    	RunnerWithConstructorInjection runnerWithConstructorInjection = (RunnerWithConstructorInjection) context.getBean("runnerWithConstructorInjectionBean");
        
    	String originalText = "This is a text !";
    	String formattedText = runnerWithConstructorInjection.format(originalText);
    	
    	assertTrue(originalText.toUpperCase().equals(formattedText));
    }
    
}
