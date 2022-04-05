package com.baeldung.performancetests.jit;

public class Fibonacci {

    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            long startTime = System.nanoTime();
            int result = fibonacci(12);
            long totalTime = System.nanoTime() - startTime;
            System.out.println(totalTime);
        }
    }

    private static int fibonacci(int index) {
        if (index <= 1)
            return index;
        return fibonacci(index-1) + fibonacci(index-2);
    }

}
