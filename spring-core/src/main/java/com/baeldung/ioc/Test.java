package com.baeldung.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @SuppressWarnings("resource")
    public static void main(String a[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("iocApplicationContext.xml");
        
        // Annotation auto wired
        ConstructorInjectionExample constructorInjectionExample 
          = context.getBean("constructorInjectionExample", ConstructorInjectionExample.class);
        constructorInjectionExample.sayHello();
        SetterMethodInjectionExample setterMethodInjectionExample 
          = context.getBean("setterMethodInjectionExample", SetterMethodInjectionExample.class);
        setterMethodInjectionExample.sayHello();
        
        // XML injection
        ConstructorInjectionExample constructorWithXml 
          = context.getBean("constructorWithXml", ConstructorInjectionExample.class);
        constructorWithXml.sayHello();
        SetterMethodInjectionExample setterWithXml 
          = context.getBean("setterWithXml", SetterMethodInjectionExample.class);
        setterWithXml.sayHello();
    }
}
