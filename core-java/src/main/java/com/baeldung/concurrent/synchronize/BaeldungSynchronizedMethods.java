package com.baeldung.concurrent.synchronize;

public class BaeldungSynchronizedMethods {

    private int sum = 0;
    private int syncSum = 0;

    public static int staticSum = 0;

    public void calculate() {
        setSum(getSum() + 1);
    }

    public synchronized void synchronisedCalculate() {
        setSyncSum(getSyncSum() + 1);
    }

    public static synchronized void syncStaticCalculate() {
        staticSum = staticSum + 1;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSyncSum() {
        return syncSum;
    }

    public void setSyncSum(int syncSum) {
        this.syncSum = syncSum;
    }
}
