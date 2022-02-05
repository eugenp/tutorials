package com.baeldung.producerconsumer;

public class Producer implements Runnable {
    private final DataQueue dataQueue;
    private boolean runFlag;

    private static int idSequence = 0;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
        runFlag = true;
    }

    @Override
    public void run() {
        try {
            produce();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void produce() throws InterruptedException {
        while (runFlag) {
            Message message = generateMessage();
            if (dataQueue.isFull()) {
                dataQueue.waitOnFull();
            }
            if (!runFlag) {
                break;
            }
            dataQueue.add(message);
            dataQueue.notifyForEmpty();
        }
        System.out.println("Producer Stopped");
    }

    private Message generateMessage() {
        Message message = new Message(++idSequence, Math.random());
        System.out.printf("Generated Message. Id: %d, Data: %f\n", message.getId(), message.getData());

        //Sleeping on random time to make it realistic
        try {
            Thread.sleep((long) (message.getData() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void stop() {
        runFlag = false;
    }
}
