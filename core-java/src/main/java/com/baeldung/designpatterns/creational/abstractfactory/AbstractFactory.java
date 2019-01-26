package com.baeldung.designpatterns.creational.abstractfactory;

public interface AbstractFactory {
    Animal getAnimal(String toyType) ;
    Color getColor(String colorType);
}