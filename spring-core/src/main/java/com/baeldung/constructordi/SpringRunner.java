package com.baeldung.constructordi;

import com.baeldung.beanfactory.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.Car;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringRunner {
    public static void main(String[] args) {
        Car toyota = getCarFromXml();

        System.out.println(toyota);

        toyota = getCarFromJavaConfig();

        System.out.println(toyota);
        
        //Loading bean property file
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("baeldung.xml");
        //Constructor based DI Injection
        
        //Instantiating a Manager
         Employee manager=(Employee) ctx.getBean("Manager");
        //Instantiating a Employee
         Employee worker=(Employee) ctx.getBean("Worker");
         
        manager.show();
         worker.show();
         
        //Setter based DI Injection
        Employee Consultant = (Employee) ctx.getBean("object");
        Consultant.show();
        
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("baeldung.xml");

        return context.getBean(Car.class);
    }
}
