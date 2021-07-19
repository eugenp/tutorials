package com.baeldung.concurrent.synchronize;

public class BaeldungSynchronizedBlocks {

    private int count = 0;
    private static int staticCount = 0;

    void performSynchronisedTask() {
        synchronized (this) {
            setCount(getCount() + 1);
        }
    }

    static void performStaticSyncTask() {
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

    static int getStaticCount() {
        return staticCount;
    }

    private static void setStaticCount(int staticCount) {
        BaeldungSynchronizedBlocks.staticCount = staticCount;
    }
}
