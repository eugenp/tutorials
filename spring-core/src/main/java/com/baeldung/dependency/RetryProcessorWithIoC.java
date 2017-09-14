package com.baeldung.dependency;

public class RetryProcessorWithIoC {

    private DBConn connection;
    private SuccessHandler sHandler;
    private int retryInterval;

    public RetryProcessorWithIoC(DBConn conn, SuccessHandler handler){
        this.connection=conn;
        this.sHandler=handler;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getRetryInterval() {
        return retryInterval;
    }
}
