package com.baeldung.bifunction;

import java.util.function.BiFunction;

public class ArithmeticOeration {
    public static BiFunction<Integer, Integer, Integer> sum(Integer a, Integer b){
        return (x, y) -> a + b;
    }

    public static BiFunction<Integer, Integer, Integer> multiply(Integer a, Integer b){
        return (x, y) -> a * b;
    }
}
