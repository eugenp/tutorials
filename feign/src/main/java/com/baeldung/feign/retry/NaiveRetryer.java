package com.baeldung.feign.retry;

import feign.RetryableException;
import feign.Retryer;

public class NaiveRetryer implements feign.Retryer {
    @Override
    public void continueOrPropagate(RetryableException e) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw e;
        }
    }

    @Override
    public Retryer clone() {
        return new NaiveRetryer();
    }
}
