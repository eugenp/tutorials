package com.insightsource.concurrent;

public class DateUtilThreadTest extends Thread {
    public void run() {
        int i1 = DateUtil.compareDateTime("20130111 01:02:03", "20130111 01:02:03");
        int i2 = DateUtil.compareDateTime("20120111 01:02:03", "20130111 01:02:03");

        System.out.println("i1=" + i1);
        System.out.println("i2=" + i2);
    }

    /*
     * һ��һ��������̫�鷳�� ʹ��junitperf.
     */
    public static void main(String args[]) {
        Thread t1 = new DateUtilThreadTest();
        t1.start();

        Thread t2 = new DateUtilThreadTest();
        t2.start();
    }
}
