package com.baeldung.producerconsumer;

public class Producer implements Runnable {
    private final DataQueue dataQueue;
    private volatile boolean runFlag;

    private static int idSequence = 0;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
        runFlag = true;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        while (runFlag) {
            synchronized (this) {
                while (dataQueue.isFull()) {
                    try {
                        dataQueue.waitOnFull();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                if (!runFlag) {
                    break;
                }
            }
            Message message = generateMessage();
            if (!dataQueue.isFull()) {
                dataQueue.add(message);
                dataQueue.notifyAllForEmpty();
            }
        }
        System.out.println("Producer Stopped");
    }

    private Message generateMessage() {
        Message message = new Message(++idSequence, Math.random());
        System.out.printf("[%s] Generated Message. Id: %d, Data: %f\n", Thread.currentThread().getName(), message.getId(), message.getData());

        //Sleeping on random time to make it realistic
        // ThreadUtil.sleep((long) (message.getData() * 100));

        return message;
    }

    public void stop() {
        runFlag = false;
        dataQueue.notifyAllForFull();
    }
}
