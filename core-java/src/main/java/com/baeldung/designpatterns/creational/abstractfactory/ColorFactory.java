package com.baeldung.designpatterns.creational.abstractfactory;

public class ColorFactory implements AbstractFactory {

    @Override
    public Color getColor(String colorType) {
        if ("Brown".equalsIgnoreCase(colorType)) {
            return new Brown();
        } else if ("White".equalsIgnoreCase(colorType)) {
            return new White();
        }

        return null;
    }

    @Override
    public Animal getAnimal(String toyType) {
        throw new UnsupportedOperationException();
    }

}
