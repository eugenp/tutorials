package com.baeldung.executorservicetest;

public class MyRunnable implements Runnable {

    Long result;

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    @Override
    public void run() {
        result = sum();
    }

    private Long sum() {
        Long result = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            result += i;
        }
        return result;
    }
}