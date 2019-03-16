package com.baeldung.lambdas;

import java.util.Random;
import java.util.stream.IntStream;

public class LambdaVariables {

    private String greeting = "Hello";

    public static void main(String[] args) {
        new LambdaVariables().workaroundMultithreading();
    }

    public void localVariableInLambda() {
        int counter = 1;
        Runnable runnable = () -> System.out.println(counter);

        runnable.run();
        new Thread(runnable).start();
    }

    public void instanceVariableInLambda() {
        Runnable runnable = () -> System.out.println(greeting);

        greeting = "hi";

        new Thread(runnable).start();
    }

    public void parameterLambda(int counter) {
        Runnable runnable = () -> System.out.println(counter);
    }

    public void workaroundSingleThread() {
        int[] holder = new int[] { 2 };
        IntStream sums = IntStream
          .of(1, 2, 3)
          .map(val -> val + holder[0]);

        holder[0] = 0;

        System.out.println(sums.sum());
    }

    public void workaroundMultithreading() {
        int[] holder = new int[] { 2 };
        Runnable runnable = () -> {
            System.out.println(IntStream
              .of(1, 2, 3)
              .map(val -> val + holder[0])
              .sum());
        };

        new Thread(runnable).start();

        // simulating some processing
        try {
            Thread.sleep(new Random().nextInt(3) * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder[0] = 0;
    }

}
