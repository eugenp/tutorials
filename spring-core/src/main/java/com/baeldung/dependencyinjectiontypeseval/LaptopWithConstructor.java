package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaptopWithConstructor {
    IGraphicsCard graphicsCard;

    public LaptopWithConstructor(IGraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDetails() {
        return graphicsCard.getDetails();
    }

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springconfig-eval.xml")) {
            LaptopWithConstructor laptop = (LaptopWithConstructor) context.getBean("LaptopWithConstructorBean");
            System.out.println(laptop.getDetails());
        }
    }
}
