package com.baeldung.reducingIfElse;

public class Modulo implements Operation {
        @Override public int apply(int a, int b) {
                return a % b;
        }
}
