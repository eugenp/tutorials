package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LaptopWithConstructorAnnotation {
    IGraphicsCard graphicsCard;

    @Autowired
    public LaptopWithConstructorAnnotation(IGraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDetails() {
        return graphicsCard.getDetails();
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            LaptopWithConstructorAnnotation laptop = context.getBean(LaptopWithConstructorAnnotation.class);
            System.out.println(laptop.getDetails());
        }
    }

}
