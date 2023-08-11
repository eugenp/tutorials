package com.baeldung.producerconsumer;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Producer implements Runnable {
    private static final Logger log = Logger.getLogger(Producer.class.getCanonicalName());
    private final DataQueue dataQueue;
    private static int idSequence = 0;
    final ReentrantLock lock = new ReentrantLock();

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
    }

    @Override
    public void run() {
        produce();
    }

    public void produce() {
        while (dataQueue.runFlag) {

            try {
                lock.lock();

                while (dataQueue.isFull() && dataQueue.runFlag) {
                    try {
                        dataQueue.waitOnFull();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                if (!dataQueue.runFlag) {
                    break;
                }

                Message message = generateMessage();
                dataQueue.add(message);
                dataQueue.notifyAllForEmpty();

                log.info("Size of the queue is: " + dataQueue.getSize());

            }
            finally{
                lock.unlock();
            }

            //Sleeping on random time to make it realistic
            ThreadUtil.sleep((long) (Math.random() * 100));
        }

        log.info("Producer Stopped");
    }

    private Message generateMessage() {
        Message message = new Message(incrementAndGetId(), Math.random());
        log.info(String.format("[%s] Generated Message. Id: %d, Data: %f%n",
                Thread.currentThread().getName(), message.getId(), message.getData()));

        return message;
    }

    private static int incrementAndGetId() {
        return ++idSequence;
    }

    public void stop() {
        dataQueue.runFlag = false;
        dataQueue.notifyAllForFull();
    }
}
