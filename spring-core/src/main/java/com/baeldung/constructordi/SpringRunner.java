package com.baeldung.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.Car;
import com.baeldung.constructordi.domain.Processor;
import com.baeldung.constructordi.domain.Telephone;

public class SpringRunner {

    public static void main(String[] args) {
        Car toyota = getCarFromXml();

        System.out.println(toyota);

        toyota = getCarFromJavaConfig();

        System.out.println(toyota);
        
        Telephone telephoneBean = getTelephoneFromXml();
        
        System.out.println(telephoneBean);
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");

        return context.getBean(Car.class);
    }
    
    private static Telephone getTelephoneFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");

        return context.getBean(Telephone.class);
    }
}
