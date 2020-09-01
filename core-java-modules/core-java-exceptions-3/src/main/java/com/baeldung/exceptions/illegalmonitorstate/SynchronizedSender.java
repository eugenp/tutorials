package com.baeldung.exceptions.illegalmonitorstate;

public class SynchronizedSender implements Runnable {
    private final Data data;
    private boolean illegalMonitorStateExceptionOccurred;

    public SynchronizedSender(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (data) {
            try {
                Thread.sleep(1000);

                data.send("test");

                data.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
                illegalMonitorStateExceptionOccurred = true;
            }
        }
    }

    public boolean hasIllegalMonitorStateExceptionOccurred() {
        return illegalMonitorStateExceptionOccurred;
    }
}
