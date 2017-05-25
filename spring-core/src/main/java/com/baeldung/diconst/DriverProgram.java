package com.baeldung.diconst;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.diconst.model.Bike;

public class DriverProgram {
    public static void main(String args[]) {
        ApplicationContext context;
        Bike bike;
        
        //getting context from spring configuration class
        context = new AnnotationConfigApplicationContext(SpringConfig.class);
        bike = (Bike) context.getBean("Bike");
        System.out.println(bike);
        
        //getting context from XML file
        context = new ClassPathXmlApplicationContext("diconst.xml");
        bike = (Bike) context.getBean("Bike");
        System.out.println(bike);
    }
}
