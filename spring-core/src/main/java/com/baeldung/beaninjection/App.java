package com.baeldung.beaninjection;

import com.baeldung.beaninjection.service.CarPool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CarPool carPool = context.getBean(CarPool.class);
        carPool.startOneCar();
        carPool.stopOneCar();
    }

}
