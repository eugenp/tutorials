package com.baeldung.hexagonal;

public class ConsoleDisplay implements Display {

    @Override
    public void showMessage(String message) {
        System.out.println(message);
        
    }
}
