package com.baeldung.concurrent.stopexecution;

import java.util.Random;

public class Step {

    private static int MAX = Integer.MAX_VALUE / 2;
    int number;

    public Step(int number) {
        this.number = number;
    }

    public void perform() throws InterruptedException {
        Random rnd = new Random();
        int target = rnd.nextInt(MAX);
        while (rnd.nextInt(MAX) != target) {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
    }
}
