package com.baeldung.exceptions.illegalmonitorstate;

public class UnSynchronizedReceiver implements Runnable {
    private final Data data;
    private String message;
    private boolean illegalMonitorStateExceptionOccurred;

    public UnSynchronizedReceiver(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            data.wait();
            this.message = data.receive();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IllegalMonitorStateException e) {
            e.printStackTrace();
            illegalMonitorStateExceptionOccurred = true;
        }
    }

    public boolean hasIllegalMonitorStateExceptionOccurred() {
        return illegalMonitorStateExceptionOccurred;
    }

    public String getMessage() {
        return message;
    }
}
