package com.baeldung.socket.read;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class SocketReadAllDataLiveTest {
    
    @Test
    public void givenServerAndClient_whenClientSendsAndServerReceivesData_thenCorrect() {
        //Run server in new thread
        Runnable runnable1 = () -> { runServer(); };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        //Wait for 10 seconds 
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Run client in a new thread
        Runnable runnable2 = () -> { runClient(); };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
    
    public static void runServer() {
        //Run Server
        Server server = new Server();
        server.runServer(5555);
    }
    
    public static void runClient() {
        //Run Client
        Client client = new Client();
        client.runClient("127.0.0.1", 5555);
    }
}