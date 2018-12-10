package com.baeldung.creational.abstractfactory;

public interface AbstractFactory {
    Animal getAnimal(String toyType) ;
    Color getColor(String colorType);
}