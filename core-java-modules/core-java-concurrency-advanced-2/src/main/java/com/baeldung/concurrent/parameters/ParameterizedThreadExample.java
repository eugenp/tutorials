package com.baeldung.concurrent.parameters;

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class ParameterizedThreadExample {

    public static void parameterisedThreadAnonymousClass() {
        final String parameter = "123";
        Thread parameterizedThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread()
                    .getName() + " : " + parameter);
            }
        });

        parameterizedThread.start();
    }

    public static Callable<Integer> sumCalculator(int... numbers) {
        return () -> numbers != null ? IntStream.of(numbers)
            .sum() : 0;
    }

    public static Callable<Double> averageCalculator(int... numbers) {
        return () -> numbers != null ? IntStream.of(numbers)
            .average()
            .orElse(0d) : 0d;
    }
}
