package com.baeldung.interfaces.polymorphysim;

import com.baeldung.interfaces.HasColor;

public interface Shape extends HasColor {

    public abstract String name();
    public abstract double area();
}
