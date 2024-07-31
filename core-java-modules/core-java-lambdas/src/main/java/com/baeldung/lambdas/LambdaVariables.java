package com.baeldung.lambdas;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Class with examples about working with capturing lambdas.
 */
public class LambdaVariables {

    private volatile boolean run = true;
    private int start = 0;

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        new LambdaVariables().localVariableMultithreading();
    }

    Supplier<Integer> incrementer(int start) {
        return () -> start; // can't modify start parameter inside the lambda
    }

    Supplier<Integer> incrementer() {
        return () -> start++;
    }

    public void localVariableMultithreading() {
        boolean run = true;
        executor.execute(() -> {
            while (run) {
                // do operation
            }
        });
        // commented because it doesn't compile, it's just an example of non-final local variables in lambdas
        // run = false;
    }

    public void instanceVariableMultithreading() {
        executor.execute(() -> {
            while (run) {
                // do operation
            }
        });

        run = false;
    }

    /**
     * WARNING: always avoid this workaround!!
     */
    public void workaroundSingleThread() {
        int[] holder = new int[] { 2 };
        IntStream sums = IntStream
          .of(1, 2, 3)
          .map(val -> val + holder[0]);

        holder[0] = 0;

        System.out.println(sums.sum());
    }

    /**
     * WARNING: always avoid this workaround!!
     */
    public void workaroundMultithreading() {
        int[] holder = new int[] { 2 };
        Runnable runnable = () -> System.out.println(IntStream
          .of(1, 2, 3)
          .map(val -> val + holder[0])
          .sum());

        new Thread(runnable).start();

        // simulating some processing
        try {
            Thread.sleep(new Random().nextInt(3) * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        holder[0] = 0;
    }

}
