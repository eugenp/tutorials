package com.baeldung.interfaces.polymorphysim;

import com.baeldung.interfaces.HasColor;

public interface Shape extends HasColor {

    String name();

    double area();
}
