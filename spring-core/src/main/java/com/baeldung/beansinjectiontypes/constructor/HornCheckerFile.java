package com.baeldung.beansinjectiontypes.constructor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HornCheckerFile {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = 
          new AnnotationConfigApplicationContext(Config.class)) {
            Dealership dealership = (Dealership) context.getBean("dealership");

            dealership.getCar().honk();
        }
    }
}
