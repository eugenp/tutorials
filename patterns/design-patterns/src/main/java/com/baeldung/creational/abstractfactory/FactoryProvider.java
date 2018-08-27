package com.baeldung.creational.abstractfactory;

public class FactoryProvider {
    public static AbstractFactory getFactory(String choice){
        
        if("Toy".equalsIgnoreCase(choice)){
            return new AnimalFactory();
        }
        else if("Color".equalsIgnoreCase(choice)){
            return new ColorFactory();
        }
        
        return null;
    }
}