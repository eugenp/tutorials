package com.baeldung.designpatterns.creational.abstractfactory;

public interface AbstractFactory {
    Toy getToy(String toyType) ;
    Color getColor(String colorType);
}