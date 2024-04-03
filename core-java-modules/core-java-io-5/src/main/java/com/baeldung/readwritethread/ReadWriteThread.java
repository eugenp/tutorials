package com.baeldung.readwritethread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadWriteThread {

    public static void readFile(String filePath) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void writeFile(String filePath, String content) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (FileWriter fileWriter = new FileWriter(filePath)) {
                    fileWriter.write("Hello, world!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        String file = "src/main/resources/text.txt";

        writeFile(file, "Hello, world!");

        readFile(file);

        // Sleep for a while to allow the threads to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
