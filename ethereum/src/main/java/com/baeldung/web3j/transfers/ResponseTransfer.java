package com.baeldung.web3j.transfers;

import java.time.Duration;

public class ResponseTransfer {

    public ResponseTransfer() {}

    private Duration performance;
    private String message;

    public Duration getPerformance() {
        return performance;
    }

    public void setPerformance(Duration performance) {
        this.performance = performance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
