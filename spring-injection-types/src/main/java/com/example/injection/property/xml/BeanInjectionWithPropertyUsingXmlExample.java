package com.example.injection.property.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInjectionWithPropertyUsingXmlExample {
    
    public static void main(String[] args) {
        
        try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("property-injection.xml")) {
            PushButton button = context.getBean(PushButton.class);
            button.press();
        }
        
    }
    
}
