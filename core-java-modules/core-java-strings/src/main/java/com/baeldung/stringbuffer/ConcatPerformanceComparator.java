package com.baeldung.stringbuffer;

public class ConcatPerformanceComparator {

    public static String concatWithString() {
        String srtr = "Spring";
        for (int i = 0; i < 10000; i++){
            str = str + "Framework";
        }
        return str;
    }

    public static String concatWithStringBuffer() {
        StringBuffer sb = new StringBuffer("Spring");
        for (int i = 0; i < 10000; i++){
            sb.append("Framework");
        }
        return sb.toString();
    }

    public static void main(String args[]) {

        long startTime = System.currentTimeMillis();

        concatWithString();
        System.out.println("Total Time String Concat: " + (System.currentTimeMillis()-startTime) + "ms");

        startTime = System.currentTimeMillis();

        concatWithStringBuffer();
        System.out.println("Total Time StringBuffer Concat: " + (System.currentTimeMillis()-startTime) + "ms");

    }
}
