package com.baeldung.junit.main.test;

import java.util.Arrays;

public class SimpleMain {

    public static void main(String[] args) {

        System.out.println("Received input parameters: " + Arrays.asList(args));

        Bootstrapper bootstrapper = new Bootstrapper(new InputReader(), new Calculator());

        bootstrapper.processRequest(args);
    }

}