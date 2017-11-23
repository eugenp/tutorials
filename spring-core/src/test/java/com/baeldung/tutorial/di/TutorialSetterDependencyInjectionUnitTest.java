package com.baeldung.tutorial.di;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TutorialSetterDependencyInjectionUnitTest {

    @Test
    public void givenXMLAppContext_WhenSetterBasedBeanIsCreated_ThenDependencyValid() {
        
    	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("tutorial-setter-di-context.xml");
    	PrintService printService = (PrintService) context.getBean("printService");
        
    	String originalMessage = "Welcome to Baeldung!. Setter DI XML";
    	String printedMessage = printService.printMessage();
    	
    	assertThat(originalMessage, equalTo(printedMessage));
    	
    	context.close();
    }
    
    @Test
    public void givenAnnotationAppContext_WhenSetterBasedBeanIsCreated_ThenDependencyValid() {
    	
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(TutorialConfigSetterDI.class);
        PrintService printService = (PrintService) context.getBean("printService");
        
        String originalMessage = "Welcome to Baeldung!. Setter DI Java";
        String printedMessage = printService.printMessage();
        
        assertThat(originalMessage, equalTo(printedMessage));
        
        context.close();
    }
    
}
