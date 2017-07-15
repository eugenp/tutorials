package com.example.injection.constructor.annotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanInjectionWithConstructorUsingAnnotationsExample {
    
    public static void main(String[] args) {
        
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.injection.constructor.annotations")) {
            PushButton button = context.getBean(PushButton.class);
            button.press();
        }
        
    }
    
}
