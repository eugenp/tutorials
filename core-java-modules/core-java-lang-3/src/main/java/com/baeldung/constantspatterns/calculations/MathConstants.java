package com.baeldung.constantspatterns.calculations;

public final class MathConstants {
    public static final double PI = 3.14159265359;
    public static final double GOLDEN_RATIO = 1.6180;
    public static final double GRAVITATIONAL_ACCELERATION = 9.8;
    public static final double EULERS_NUMBER = 2.7182818284590452353602874713527;

    public enum Operation {
        ADD, SUBTRACT, DIVIDE, MULTIPLY
    }

    private MathConstants() {

    }
}
