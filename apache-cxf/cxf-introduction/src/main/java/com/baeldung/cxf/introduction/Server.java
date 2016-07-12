package com.baeldung.cxf.introduction;

import javax.xml.ws.Endpoint;

public class Server {
    private Server() {
        BaeldungImpl implementor = new BaeldungImpl();
        String address = "http://localhost:8080/baeldung";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws InterruptedException {
        new Server();
        System.out.println("Server ready");
        Thread.sleep(60 * 1000);        
        System.out.println("Server exiting");
        System.exit(0);
    }
}