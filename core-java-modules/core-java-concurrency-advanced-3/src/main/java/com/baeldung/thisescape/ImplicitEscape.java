package com.baeldung.thisescape;

public class ImplicitEscape {

    public ImplicitEscape() {
        Thread t = new Thread() {

            @Override
            public void run() {
                System.out.println("Started...");
            }
        };

        t.start();
    }
}
