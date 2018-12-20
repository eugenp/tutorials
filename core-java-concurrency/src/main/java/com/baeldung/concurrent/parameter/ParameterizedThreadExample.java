package com.baeldung.concurrent.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
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

    public static Runnable numberGenerator(BlockingQueue<Integer> topic) {
        return () -> {
            IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    try {
                        Thread.sleep(50);
                        topic.put(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            try {
                topic.put(-1); // This marks the end of the message.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static Callable<Double> averageCalculator(BlockingQueue<Integer> topic) {
        return () -> {
            List<Integer> numbers = new ArrayList<>();
            try {
                int i;
                while ((i = topic.take()) > 0) {
                    numbers.add(i);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return numbers.stream()
                .collect(Collectors.averagingInt(v -> v));
        };
    }
}
