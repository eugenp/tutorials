package com.baeldung.stopexecution;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;


public class StopExecutionFurtherCode {

    private boolean shouldContinue = true;

    public int performTask(int a, int b){
        if (!shouldContinue){
            System.exit(0);
        }
        return a + b;
    }

    public int calculateFactorial(int n) {
        if (n <= 1) {
            return 1; // base case
        }

        return n * calculateFactorial(n - 1);
    }

    public int calculateSum(int[] x){
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            if (x[i] < 0){
                break;
            }
            sum += x[i];
        }
        return sum;
    }


    public <T> T stopExecutionUsingException(T object){
        if (object instanceof Number){
            throw new IllegalArgumentException("Parameter can not be number.");
        }
        T upperCase = (T)String.valueOf(object).toUpperCase(Locale.ENGLISH);
        return upperCase;
    }


    public int processLines(String[] lines){
        int statusCode = 0;
        parser: for (String line : lines) {
            System.out.println("Processing line: " + line);

            if (line.equals("stop")) {
                System.out.println("Stopping parsing...");
                statusCode = -1;
                break parser; // Stop parsing and exit the loop
            }

            System.out.println("Line processed.");
        }
        return statusCode;
    }

    public void download(String fileUrl, String destinationPath) throws MalformedURLException {
        if (fileUrl == null || fileUrl.isEmpty() || destinationPath == null || destinationPath.isEmpty()) {
            return;
        }
        // execute downloading
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream(); FileOutputStream out = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(){
        shouldContinue = false;
    }

}
