package com.baeldung.javafeatures;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Hello");
        MultiFileExample mm = new MultiFileExample();
        mm.ping(args[0]);
    }
}
