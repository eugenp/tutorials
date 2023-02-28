package com.baeldung.pipeline.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public class BiFunctionExample {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        BiFunction<Integer, Integer, Integer> mul = (a, b) -> a * b;
        Function<Integer, String> toString = Object::toString;
        BiFunction<Integer, Integer, String> pipeline
            = add.andThen(a -> mul.apply(a, 2)).andThen(toString);
        String result = pipeline.apply(1, 2);
        System.out.println(result);
    }
}
