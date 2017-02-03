package com.baeldung.thrift;

public class Application {

    public static void main(String[] args) {
        CrossPlatformServiceServer server = new CrossPlatformServiceServer();
        server.start();
    }
}
