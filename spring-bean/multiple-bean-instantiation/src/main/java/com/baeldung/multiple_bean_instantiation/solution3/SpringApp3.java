package com.baeldung.multiple_bean_instantiation.solution3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.multiple_bean_instantiation.solution3.Person;

/**
 * Hello world!
 *
 */
public class SpringApp3 
{
    public static void main( String[] args )
    {
    	// Initializing the spring container
//    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersonConfig.class);
    	
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	
		
		  Person person = context.getBean("personOne", Person.class);
		  
		  System.out.println(person);
		 
    	
    	context.close();
    }
}
