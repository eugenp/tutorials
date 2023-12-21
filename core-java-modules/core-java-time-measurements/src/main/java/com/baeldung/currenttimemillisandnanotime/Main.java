package com.baeldung.currenttimemillisandnanotime;


public class Main {
    public static void main(String[] args) {
        System.out.println("Logging timestamp: " + UtilizingCurrentTimeMillisMethod.logTimestamp());
        System.out.println("Time taken to perform task: " + UtilizingCurrentTimeMillisMethod.measureTimeDuration() + " milliseconds");
        System.out.println("Time taken to perform short task: " + UtilizingNanoTime.measureShortDuration() + " nanoseconds");
    }
}