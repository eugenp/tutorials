package com.baeldung.string;

public class StringBufferStringBuilder {

    public static void main(String[] args) {

        int iterations = 10000000;

        System.gc();
        long startTime = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer("abc");
        for (int i = 0; i < iterations; i++) {
            stringBuffer.append("def");
        }
        System.out.println("Time taken by StringBuffer: " + (System.currentTimeMillis() - startTime) + "ms"); // Time taken by StringBuffer: 394ms

        System.gc();
        startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder("abc");
        for (int i = 0; i < iterations; i++) {
            stringBuilder.append("def");
        }
        System.out.println("Time taken by StringBuilder: " + (System.currentTimeMillis() - startTime) + "ms"); // Time taken by StringBuilder: 129ms
    }
}
