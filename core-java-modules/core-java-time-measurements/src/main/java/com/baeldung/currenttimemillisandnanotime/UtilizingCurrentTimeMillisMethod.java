package com.baeldung.currenttimemillisandnanotime;

public class UtilizingCurrentTimeMillisMethod {
    public static long logTimestamp() {
        return System.currentTimeMillis();
    }

    static long measureTimeDuration() {
        long startTime = System.currentTimeMillis();

        performTask();

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static void performTask() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
