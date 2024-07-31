package com.baeldung.readwritethread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadWriteBlockingQueue {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String readFileName = "src/main/resources/read_file.txt";
        String writeFileName = "src/main/resources/write_file.txt";

        Thread producerThread = new Thread(new FileProducer(queue, readFileName));
        Thread consumerThread1 = new Thread(new FileConsumer(queue, writeFileName));

        producerThread.start();
        Thread.sleep(100); // Give producer a head start
        consumerThread1.start();
        try {
            producerThread.join();
            consumerThread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class FileProducer implements Runnable {

    private final BlockingQueue<String> queue;
    private final String inputFileName;

    public FileProducer(BlockingQueue<String> queue, String inputFileName) {
        this.queue = queue;
        this.inputFileName = inputFileName;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.offer(line);
                System.out.println("Producer added line: " + line);
                System.out.println("Queue size: " + queue.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class FileConsumer implements Runnable {

    private final BlockingQueue<String> queue;
    private final String outputFileName;

    public FileConsumer(BlockingQueue queue, String outputFileName) {
        this.queue = queue;
        this.outputFileName = outputFileName;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            String line;
            while ((line = queue.poll()) != null) {
                writer.write(line);
                writer.newLine();
                System.out.println(Thread.currentThread()
                    .getId() + " - Consumer processed line: " + line);
                System.out.println("Queue size: " + queue.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}