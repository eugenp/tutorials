package com.insightsource.thread;

public class ThreadJoinTest {
    public static void main(String[] args) {
        int times = 10;
        Thread[] ts = new Thread[times];

        for (int i = 0; i < times; i++) {
            Thread t = new Test(i);
            ts[i] = t;
            t.start();
        }

        for (int i = 0; i < times; i++) {
            try {
                ts[i].join();    //main thread will wait until all ts thread finish.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("done.");
    }


}

class Test extends Thread {
    private int num;

    public Test(int n) {
        this.num = n;
    }

    @Override
    public void run() {
        System.out.println(this.num);
    }
}
