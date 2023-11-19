package com.baeldung.dumps;

public class ThreadDump {
    public static void main(String[] args) {
        longRunningTask();
    }

    private static void longRunningTask() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted!");
                break;
            }
            System.out.println(i);
        }
    }

}
