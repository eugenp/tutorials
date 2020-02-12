package com.baeldung.exceptions.keywords.finallykeyword;

public class FinallyExample {

    public static void main(String args[]) throws Exception {
        try {
            System.out.println("Execute try block");
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Execute catch block");
        } finally {
            System.out.println("Execute finally block");
        }

        try {
            System.out.println("Execute try block");
        } finally {
            System.out.println("Execute finally block");
        }

        try {
            System.out.println("Execute try block");
            throw new Exception();
        } finally {
            System.out.println("Execute finally block");
        }
    }

}
