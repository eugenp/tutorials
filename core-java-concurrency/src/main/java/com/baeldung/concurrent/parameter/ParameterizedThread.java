package com.baeldung.concurrent.parameter;

public class ParameterizedThread implements Runnable {

    String parameter;

    public ParameterizedThread(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread()
            .getName() + " : " + parameter);
    }
}
