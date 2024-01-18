package com.baeldung.readwritethread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReadWriteBlockingQueue {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String inputFileName = "src/main/resources/read_file.txt";
        String outputFileName = "src/main/resources/write_file.txt";

        Thread producerThread = new Thread(new FileProducer(queue, inputFileName));
        Thread consumerThread = new Thread(new FileConsumer(queue, outputFileName));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join(); // Wait for producer to finish
            consumerThread.join(); // Wait for consumer to finish
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}