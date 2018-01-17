package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baeldung.beaninjection.domain.ComputerWithConstructorInjection;
import com.baeldung.beaninjection.domain.ComputerWithSetterInjection;

@ComponentScan("com.baeldung.beaninjection.domain")
public class Application {

    public static void main(String[] args) {
        ApplicationContext javaConfigContext = new AnnotationConfigApplicationContext(Application.class);
        printBeansFromContext(javaConfigContext);

        ApplicationContext xmlConfigContext = new ClassPathXmlApplicationContext("beaninjection.xml");
        printBeansFromContext(xmlConfigContext);
    }

    private static void printBeansFromContext(ApplicationContext context) {
        ComputerWithConstructorInjection computer1 = context.getBean(ComputerWithConstructorInjection.class);
        System.out.println("ComputerFromXmlConfig: " + computer1);
        ComputerWithSetterInjection computer2 = context.getBean(ComputerWithSetterInjection.class);
        System.out.println("ComputerFromXmlConfig: " + computer2);
    }

}
