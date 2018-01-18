package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaptopWithSetter {
    IGraphicsCard graphicsCard;

    public void setGraphicsCard(IGraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDetails() {
        return graphicsCard.getDetails();
    }

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springconfig-eval.xml")) {
            LaptopWithSetter laptop = (LaptopWithSetter) context.getBean("LaptopWithSetterBean");
            System.out.println(laptop.getDetails());
        }
    }

}
