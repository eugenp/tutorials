package com.baeldung.beaninjection;

import com.baeldung.beaninjection.service.CarPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CarPool carPool = context.getBean(CarPool.class);
        carPool.startOneCar();
        carPool.stopOneCar();
    }

}
