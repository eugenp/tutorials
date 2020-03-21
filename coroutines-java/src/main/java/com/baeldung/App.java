package com.baeldung;

import co.paralleluniverse.fibers.Fiber;

public class App {
    public static void main(String[] args) {
        new Fiber<Void>(() -> {
            System.out.println("Inside fiber coroutine...");
        }).start();
    }
}
