package com.baeldung.producerconsumer;

import java.util.logging.Logger;

public class Consumer implements Runnable {
    private static final Logger log = Logger.getLogger(Consumer.class.getCanonicalName());
    private final DataQueue dataQueue;

    public Consumer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        consume();
    }

    public void consume() {
        while (dataQueue.runFlag) {
            while (dataQueue.isEmpty() && dataQueue.runFlag) {
                try {
                    dataQueue.waitOnEmpty();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            if (!dataQueue.runFlag) {
                break;
            }
            Message message = dataQueue.remove();
            dataQueue.notifyAllForFull();
            useMessage(message);

        }
        log.info("Consumer Stopped");
    }

    private void useMessage(Message message) {
        if (message != null) {
            log.info(String.format("[%s] Consuming Message. Id: %d, Data: %f%n",
                    Thread.currentThread().getName(), message.getId(), message.getData()));

            //Sleeping on random time to make it realistic
            ThreadUtil.sleep((long) (message.getData() * 100));
        }
    }

    public void stop() {
        dataQueue.runFlag = false;
        dataQueue.notifyAllForEmpty();
    }
}
