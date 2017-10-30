package com.baeldung.creationaldp.abstractfactory;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractPatternDriverTest {
    @Test
    public void givenAbstractFactory_whenGettingObjects_thenSuccessful() {
        //creating a brown toy dog
        AbstractFactory toyFactory = FactoryProvider.getFactory("Toy");
        Toy toy = toyFactory.getToy("Dog");
        AbstractFactory colorFactory = FactoryProvider.getFactory("Color");
        Color color = colorFactory.getColor("Brown");
        System.out.println("A " + toy.getToy() + " with " + color.getColor() + " color " + toy.makeSound());
        
        String result = "A " + toy.getToy() + " with " + color.getColor() + " color " + toy.makeSound();
        assertEquals("A Dog with brown color Barks", result);
    }

}
