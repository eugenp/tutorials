package com.baeldung.concurrent.startathread;

public class NewThread extends Thread {
    public void run() {
    	long startTime = System.currentTimeMillis();
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + ": New Thread is running..." + i);
                try {
                    //Wait for one sec so it doesn't print too fast
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // prevent the Thread to run forever. It will finish it's execution after 2 seconds
            if (System.currentTimeMillis() - startTime > 2000) {
            	Thread.currentThread().interrupt();
            	break;
            }
        }
    }
}
