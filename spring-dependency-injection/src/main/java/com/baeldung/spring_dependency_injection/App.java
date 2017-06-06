package com.baeldung.spring_dependency_injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {        
    	ApplicationContext contextJava = new AnnotationConfigApplicationContext(Config.class);
    	
    	Person personJavaConstructor = (Person) contextJava.getBean("personJavaConstructor");
    	personJavaConstructor.printPerson();

    	Person personJavaSetter = (Person) contextJava.getBean("personJavaSetter");
    	personJavaSetter.printPerson();

    	ApplicationContext contextXml = new ClassPathXmlApplicationContext("spring-config.xml");
    	
    	Person personXmlConstructor = (Person) contextXml.getBean("personXmlConstructor");
    	personXmlConstructor.printPerson();

    	Person personXmlSetter = (Person) contextXml.getBean("personXmlSetter");
    	personXmlSetter.printPerson();
    }
}
