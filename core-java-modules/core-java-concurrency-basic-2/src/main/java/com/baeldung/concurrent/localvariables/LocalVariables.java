package com.baeldung.concurrent.localvariables;

import java.security.SecureRandom;

public class LocalVariables implements Runnable {
    private int field;

    public static void main(String... args) {
        LocalVariables target = new LocalVariables();
        new Thread(target).start();
        new Thread(target).start();
    }

    @Override
    public void run() {
        field = new SecureRandom().nextInt();
        int local = new SecureRandom().nextInt();
        System.out.println(field + " - " + local);
    }
}
