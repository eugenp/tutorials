package com.baeldung.concurrent.daemon;

public class NewThread extends Thread {

    public void run() {
    	long startTime = System.currentTimeMillis();
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println("New Thread is running..." + i);
            }
            
            // prevent the Thread to run forever. It will finish it's execution after 2 seconds
            if (System.currentTimeMillis() - startTime > 2000) {
            	Thread.currentThread().interrupt();
            	break;
            }
        }
    }
}
