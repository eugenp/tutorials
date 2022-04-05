package com.baeldung.autofactory.custom;

import com.google.auto.factory.AutoFactory;

/**
 * @author aiet
 */
@AutoFactory(extending = AbstractFactory.class)
public class CustomPhone {

    private final String brand;

    public CustomPhone(String brand) {
        this.brand = brand;
    }
}
