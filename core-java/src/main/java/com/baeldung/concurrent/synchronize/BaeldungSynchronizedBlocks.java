package com.baeldung.concurrent.synchronize;

public class BaeldungSynchronizedBlocks {

    private int count = 0;
    private static int staticCount = 0;

    public void performSynchronisedTask() {
        synchronized (this) {
            setCount(getCount() + 1);
        }
    }

    public static void performStaticSyncTask() {
        synchronized (BaeldungSynchronizedBlocks.class) {
            setStaticCount(getStaticCount() + 1);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static int getStaticCount() {
        return staticCount;
    }

    public static void setStaticCount(int staticCount) {
        BaeldungSynchronizedBlocks.staticCount = staticCount;
    }
}
