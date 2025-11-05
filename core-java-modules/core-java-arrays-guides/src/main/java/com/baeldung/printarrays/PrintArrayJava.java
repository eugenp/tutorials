package com.baeldung.printarrays;
import java.util.Arrays;

public class PrintArrayJava {
    // Print array content using a for loop
    public String printArrayUsingForLoop(String[] empArray) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < empArray.length; i++) {
            result.append(empArray[i]).append(" ");
        }
        return result.toString().trim();
    }

    // Print array content using a for-each loop
    public String printArrayUsingForEachLoop(String[] empArray) {
        StringBuilder result = new StringBuilder();
        for (String arr : empArray) {
            result.append(arr).append("\n");
        }
        return result.toString().trim();
    }

    // Print array content using Arrays.toString
    public String printArrayUsingToString(int[] empIDs) {
        return Arrays.toString(empIDs);
    }

    // Print array content using Arrays.asList
    public String printArrayUsingAsList(String[] empArray) {
        return Arrays.asList(empArray).toString();
    }

    // Print array content using Streams
    public String printArrayUsingStreams(String[] empArray) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(empArray).forEach(e -> result.append(e).append("\n"));
        return result.toString().trim();
    }
    
    // Print array content using string.join() 
    public String printArrayUsingJoin(String[] empArray) {
    	 return String.join("\n", empArray).trim();
    }
}
