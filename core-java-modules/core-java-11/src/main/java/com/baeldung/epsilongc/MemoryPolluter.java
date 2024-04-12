package com.baeldung.epsilongc;

public class MemoryPolluter {

    private static final int MEGABYTE_IN_BYTES = 1024 * 1024;
    private static final int ITERATION_COUNT = 1024 * 10;

    public static void main(String[] args) {
        System.out.println("Starting pollution");

        for (int i = 0; i < ITERATION_COUNT; i++) {
            byte[] array = new byte[MEGABYTE_IN_BYTES];
        }

        System.out.println("Terminating");
    }

}
