package com.baeldung.springamqp.exponentialbackoff;

import org.springframework.amqp.core.Queue;

public class RetryQueues {
    private Queue[] queues;
    private long initialInterval;
    private double factor;
    private long maxWait;

    public RetryQueues(long initialInterval, double factor, long maxWait, Queue... queues) {
        this.queues = queues;
        this.initialInterval = initialInterval;
        this.factor = factor;
        this.maxWait = maxWait;
    }

    public boolean retriesExhausted(int retry) {
        return retry >= queues.length;
    }

    public String getQueueName(int retry) {
        return queues[retry].getName();
    }

    public long getTimeToWait(int retry) {
        double time = initialInterval * Math.pow(factor, (double) retry);
        if (time > maxWait) {
            return maxWait;
        }

        return (long) time;
    }
}