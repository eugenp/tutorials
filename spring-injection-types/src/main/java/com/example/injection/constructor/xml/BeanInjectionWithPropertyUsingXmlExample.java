package com.example.injection.constructor.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInjectionWithPropertyUsingXmlExample {
    
    public static void main(String[] args) {
        
        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("constructor-injection.xml")) {
            PushButton button = context.getBean(PushButton.class);
            button.press();
        }
        
    }
    
}
