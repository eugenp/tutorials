package com.baeldung.cxf.introduction;

import jakarta.xml.ws.Endpoint;

public class Server {
    public static void main(String args[]) throws InterruptedException {
        BaeldungImpl implementor = new BaeldungImpl();
        String address = "http://localhost:8080/baeldung";
        Endpoint.publish(address, implementor);
        System.out.println("Server ready...");
        Thread.sleep(60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}