package com.baeldung.concurrent.waitandnotify;

public class Data {
    private String packet;
    
    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;
 
    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        transfer = true;

        notifyAll();
        return packet;
    }
 
    public synchronized void send(String packet) {
        while (!transfer) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        transfer = false;
        
        this.packet = packet;
        notifyAll();
    }
}