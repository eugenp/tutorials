package com.baeldung.threadlocalrandom;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomExample {

    public static void main(String[] args) {

        System.out.println("Random integer number: " + ThreadLocalRandom.current().nextInt());
        System.out.println("Random integer number between 500 and 1000: " + ThreadLocalRandom.current().nextInt(0, 100));
        
        System.out.println("Random long number: " + ThreadLocalRandom.current().nextLong());
        System.out.println("Random long number between 500 and 1000: " + ThreadLocalRandom.current().nextLong(0l, 100l));
        
        System.out.println("Random double number: " + ThreadLocalRandom.current().nextDouble());
        System.out.println("Random double number between 500 and 1000: " + ThreadLocalRandom.current().nextDouble(0D, 100D));
        
    }

}
