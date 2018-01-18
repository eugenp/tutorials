package com.baeldung.eval.sdi;

public class Laptop implements Computer {

    @Override
    public void startTyping() {
        System.out.println("Typing using computer");
    }

}
