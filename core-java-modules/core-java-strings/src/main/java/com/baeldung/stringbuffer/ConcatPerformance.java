package com.baeldung.stringbuffer;

import org.apache.commons.lang3.time.StopWatch;

public class ConcatPerformance {

    public static long getStringConcatenationTime() {
        StopWatch sw = StopWatch.create();
        sw.start();
        String str = "Spring";
        for (int i = 0; i < 10000; i++) {
            str += "Framework";
        }
        sw.split();
        sw.stop();
        return sw.getSplitTime();
    }

    public static long getStringBufferConcatenationTime() {
        StopWatch sw = StopWatch.create();
        sw.start();
        StringBuffer sb = new StringBuffer("Spring");
        sb.append("Framework".repeat(10000));
        sw.split();
        sw.stop();
        return sw.getSplitTime();
    }

    public static void main(String[] args) {
        System.out.println("Total Time String Concat: " + (getStringConcatenationTime() + "ms"));
        System.out.println("Total Time StringBuffer Concat: " + (getStringBufferConcatenationTime()) + "ms");
    }
}
