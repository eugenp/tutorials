package com.example.injection.property.annotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanInjectionWithPropertyUsingAnnotationsExample {
    
    public static void main(String[] args) {
        
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.injection.property.annotations")) {
            PushButton button = context.getBean(PushButton.class);
            button.press();
        }
        
    }
    
}
