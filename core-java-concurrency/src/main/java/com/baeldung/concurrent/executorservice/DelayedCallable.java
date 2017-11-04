package com.baeldung.concurrent.executorservice;

import java.util.concurrent.Callable;

public class DelayedCallable implements Callable<String> {
    
    private String name;
    private long period;

    public DelayedCallable(String name, long period) {
        this.name = name;
        this.period = period;
    }

    public String call() {

        try {
            Thread.sleep(period);
        } catch (InterruptedException ex) {
            // handle exception
            ex.printStackTrace();
        }

        return name;
    }
}
