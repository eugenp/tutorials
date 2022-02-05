package com.baeldung.producerconsumer;

public class Consumer implements Runnable {
    private final DataQueue dataQueue;
    private boolean runFlag;

    public Consumer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
        runFlag = true;
    }

    @Override
    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume() throws InterruptedException {
        while (runFlag) {
            Message message;
            if (dataQueue.isEmpty()) {
                dataQueue.waitOnEmpty();
            }
            if (!runFlag) {
                break;
            }
            message = dataQueue.remove();
            dataQueue.notifyForFull();
            useMessage(message);
        }
        System.out.println("Consumer Stopped");
    }

    private void useMessage(Message message) {
        if (message != null) {
            System.out.printf("Consuming Message. Id: %d, Data: %f\n", message.getId(), message.getData());

            //Sleeping on random time to make it realistic
            try {
                Thread.sleep((long) (message.getData() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        runFlag = false;
    }
}
