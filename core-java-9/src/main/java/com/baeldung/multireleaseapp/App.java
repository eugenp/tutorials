package com.baeldung.multireleaseapp;

public class App {

    public static void main(String[] args) throws Exception {
        String dateToCheck = args[0];
        boolean isLeapYear = DateHelper.checkIfLeapYear(dateToCheck);
        System.out.println("Date given " + dateToCheck + " is leap year: " + isLeapYear);
    }

}
