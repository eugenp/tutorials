package com.baeldung.eval.cdi;

public class Laptop implements Computer {

    @Override
    public void startTyping() {
        System.out.println("Typing using computer");
    }

}
