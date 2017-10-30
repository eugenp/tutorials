package com.baeldung.creationaldp.abstractfactory;

public class AbstractPatternDriver {
    public static void main(String[] args) {
        //creating a brown toy dog
        AbstractFactory toyFactory = FactoryProvider.getFactory("Toy");
        Toy toy = toyFactory.getToy("Dog");
        AbstractFactory colorFactory = FactoryProvider.getFactory("Color");
        Color color = colorFactory.getColor("Brown");
        System.out.println("A " + toy.getToy() + " with " + color.getColor() + " color " + toy.makeSound());
    }
}
