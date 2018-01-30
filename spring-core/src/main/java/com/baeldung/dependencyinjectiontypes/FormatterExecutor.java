package com.baeldung.dependencyinjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FormatterExecutor {

    @SuppressWarnings("resource")
    public static void main(String[] args) {    	
        ApplicationContext context = new ClassPathXmlApplicationContext("dependencyinjectiontypes-context.xml");
        RunnerWithSetterInjection setterInjection = (RunnerWithSetterInjection) context.getBean("runnerWithSetterInjectionBean");
        RunnerWithConstructorInjection constructorInjection = (RunnerWithConstructorInjection) context.getBean("runnerWithConstructorInjectionBean");
        
        System.out.println(setterInjection.format("This is a spring bean injected by  setter based injection!"));
        
        System.out.println(constructorInjection.format("This is a spring bean injected by  constructor based injection!"));
    }
}
