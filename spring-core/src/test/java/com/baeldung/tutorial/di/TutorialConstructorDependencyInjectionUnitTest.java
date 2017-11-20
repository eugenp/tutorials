package com.baeldung.tutorial.di;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TutorialConstructorDependencyInjectionUnitTest {

    @Test
    public void givenXMLAppContext_WhenConstructorBasedBeanIsCreated_ThenDependencyValid() {
        
    	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("tutorial-constructor-di-context.xml");
    	PrintService printService = (PrintService) context.getBean("printService");
        
    	String originalMessage = "Welcome to Baeldung!. Constructor DI XML";
    	String printedMessage = printService.printMessage();
    	
    	assertThat(originalMessage, equalTo(printedMessage));
    	
    	context.close();
    }
    
    @Test
    public void givenAnnotationAppContext_WhenConstructorBasedBeanIsCreated_ThenDependencyValid() {
    	
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(TutorialConfigConstructorDI.class);
        PrintService printService = (PrintService) context.getBean("printService");
        
        String originalMessage = "Welcome to Baeldung!. Constructor DI Java";
        String printedMessage = printService.printMessage();
        
        assertThat(originalMessage, equalTo(printedMessage));
        
        context.close();
    }
    
}
