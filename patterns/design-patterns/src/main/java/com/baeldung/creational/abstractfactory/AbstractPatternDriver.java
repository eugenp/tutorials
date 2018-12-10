package com.baeldung.creational.abstractfactory;

public class AbstractPatternDriver {
    public static void main(String[] args) {
        AbstractFactory abstractFactory;
        
        //creating a brown toy dog
        abstractFactory = FactoryProvider.getFactory("Toy");
        Animal toy = abstractFactory.getAnimal("Dog");
        
        abstractFactory = FactoryProvider.getFactory("Color");
        Color color = abstractFactory.getColor("Brown");
        
        String result = "A " + toy.getType() + " with " + color.getColor() + " color " + toy.makeSound();
        
        System.out.println(result);
    }
}
