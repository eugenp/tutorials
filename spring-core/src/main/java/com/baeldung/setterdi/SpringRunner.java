package com.baeldung.setterdi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.setterdi.Config;
import com.baeldung.setterdi.domain.Car;
import com.baeldung.setterdi.domain.Movie;

public class SpringRunner {
    public static void main(String[] args) {
        Car toyota = getCarFromXml();

        System.out.println(toyota);

        toyota = getCarFromJavaConfig();

        System.out.println(toyota);

        Movie minions = getMovieFromJavaConfig();
        
        System.out.println(minions);
    }

    private static Car getCarFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Car.class);
    }

    private static Car getCarFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("setterdi.xml");

        return context.getBean(Car.class);
    }
    
    private static Movie getMovieFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Movie.class);
    }
}