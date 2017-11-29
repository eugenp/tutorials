package com.baeldung.nestedclass;

public class NewEnclosing {

    void run() {
        class Local {

            void run() {
                System.out.println("Welcome to Baeldung!");
            }
        }
        Local local = new Local();
        local.run();
    }
}