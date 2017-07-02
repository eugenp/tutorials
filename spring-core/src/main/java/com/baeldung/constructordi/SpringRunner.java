package com.baeldung.constructordi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.constructordi.domain.Car;
import com.baeldung.constructordi.domain.Movie;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("constructordi.xml");

        return context.getBean(Car.class);
    }
    
    private static Movie getMovieFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        return context.getBean(Movie.class);
    }
}
