package com.baeldung.exceptions.sneakythrows;

import lombok.SneakyThrows;

public class SneakyRunnable implements Runnable {

    @SneakyThrows
    public void run() {
        try {
            throw new InterruptedException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new SneakyRunnable().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
