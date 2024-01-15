package com.baeldung.printmessagewithoutmain;

public final class PrintMessageWithoutMainMethod {

    //Using Static Blocks
    static {
        System.out.println("Hello World!!");
        System.exit(0);
    }

    //Using Nested Classes
    static {
        NestedClass.printMessage();
    }

    //Executing Code During Class Initialization
    private static final int STATUS = getStatus();
    private static int getStatus() {
        System.out.println("Hello World!!");
        System.exit(0);
        return 0;
    }

    public static void main(String[] args) {

    }

    static class NestedClass {
        static void printMessage() {
            System.out.println("Message from nested class");
        }
    }
}
