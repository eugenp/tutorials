package com.baeldung.creational.abstractfactory;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractPatternIntegrationTest {
    @Test
    public void givenAbstractFactory_whenGettingObjects_thenSuccessful() {
        AbstractFactory abstractFactory;
        
        //creating a brown toy dog
        abstractFactory = FactoryProvider.getFactory("Toy");
        Animal toy = abstractFactory.getAnimal("Dog");
        
        abstractFactory = FactoryProvider.getFactory("Color");
        Color color = abstractFactory.getColor("Brown");
        
        String result = "A " + toy.getType() + " with " + color.getColor() + " color " + toy.makeSound();
        assertEquals("A Dog with brown color Barks", result);
    }

}
