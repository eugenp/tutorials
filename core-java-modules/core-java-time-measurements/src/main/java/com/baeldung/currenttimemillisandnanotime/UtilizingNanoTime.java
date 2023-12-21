package com.baeldung.currenttimemillisandnanotime;

public class UtilizingNanoTime {

    public static long measureShortDuration() {
        long startNanoTime = System.nanoTime();

        performShortTask();

        long endNanoTime = System.nanoTime();
        return endNanoTime - startNanoTime;
    }

    private static void performShortTask() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
