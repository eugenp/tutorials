package com.baeldung.dependency;

public class RetryProcessorWithoutIoC {
    private DBConn connection= new DBConn();
    private SuccessHandler sHandler= new SuccessHandler();
    private int retryInterval;

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getRetryInterval() {
        return retryInterval;
    }
}
