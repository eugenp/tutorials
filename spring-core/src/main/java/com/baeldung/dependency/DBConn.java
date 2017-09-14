package com.baeldung.dependency;

public class DBConn {

    int connectionCount;
    Object connection;

    //getters and Setters
    public void connect(){
        System.out.print("Connecting...");
    }
}
