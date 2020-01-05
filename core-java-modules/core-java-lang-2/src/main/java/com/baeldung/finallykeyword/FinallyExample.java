package com.baeldung.finallykeyword;

public class FinallyExample {

    public void printCount(String count) {
        try {
            System.out.println("The count is " + Integer.parseInt(count));
        } catch (NumberFormatException e) {
            System.out.println("No count");
        } finally {
            System.out.println("In finally");
        }
    }
}
