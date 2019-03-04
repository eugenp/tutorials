package com.baeldung.socket.read;

import java.util.concurrent.TimeUnit;

public class TestSocketRead {

    public static void main(String[] args) {
        TestSocketRead t = new TestSocketRead();
        //Run server in new thread
        Runnable runnable1 = () -> { t.runServer(); };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        //Wait for 10 seconds 
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Run client in a new thread
        Runnable runnable2 = () -> { t.runClient(); };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
    
    void runServer() {
        //Run Server
        Server server = new Server();
        server.runServer(5555);
    }
    
    void runClient() {
        //Run Client
        Client client = new Client();
        client.runClient("127.0.0.1", 5555);
    }

}