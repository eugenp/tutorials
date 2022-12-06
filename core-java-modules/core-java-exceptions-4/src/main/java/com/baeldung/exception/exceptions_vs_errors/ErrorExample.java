package com.baeldung.exception.exceptions_vs_errors;

public class ErrorExample {
    
    public static void main(String[] args) {
        overflow();
    }

    public static void overflow() {
        System.out.println("overflow...");
        overflow();
    }

}
