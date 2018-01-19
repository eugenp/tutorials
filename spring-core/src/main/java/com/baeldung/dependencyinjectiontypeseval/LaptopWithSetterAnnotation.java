package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LaptopWithSetterAnnotation {
    IGraphicsCard graphicsCard;

    @Autowired
    public void setGraphicsCard(IGraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDetails() {
        return graphicsCard.getDetails();
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            LaptopWithSetterAnnotation laptop = context.getBean(LaptopWithSetterAnnotation.class);
            System.out.println(laptop.getDetails());
        }
    }
}
