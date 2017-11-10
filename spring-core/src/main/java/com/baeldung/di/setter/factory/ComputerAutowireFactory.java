package com.baeldung.di.setter.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.setter.config.SpringAutowireSetterContext;
import com.baeldung.di.setter.model.autowire.Computer;

public class ComputerAutowireFactory {

    public static void main(String[] args) {
        // Create Spring Application Context
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringAutowireSetterContext.class);

        // Get Spring bean "computer" created
        Computer computer = (Computer) context.getBean("computer");
        System.out.println("---------------- Setter Injection via @Autowire ------------------");
        System.out.println("Processor cores : " + computer.getProcessor()
            .getCores());
        System.out.println("Processor frequency : " + computer.getProcessor()
            .getFrequency());
        System.out.println("Memory Size in GB : " + computer.getMemory()
            .getSizeInGb());
        System.out.println("Memory format : " + computer.getMemory()
            .getFormat());
        System.out.println("---------------- Setter Injection via @Autowire ------------------");
        // Close Spring Application Context
        ((ConfigurableApplicationContext) context).close();
    }

}
