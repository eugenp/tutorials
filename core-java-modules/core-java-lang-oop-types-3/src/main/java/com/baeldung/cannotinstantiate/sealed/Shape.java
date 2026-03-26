package com.baeldung.cannotinstantiate.sealed;

public sealed interface Shape permits Circle, Rectangle, Triangle {
    double area();
}
