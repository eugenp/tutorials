package com.baeldung.creationaldp.abstractfactory;

public interface AbstractFactory {
    Toy getToy(String toyType) ;
    Color getColor(String colorType);
}