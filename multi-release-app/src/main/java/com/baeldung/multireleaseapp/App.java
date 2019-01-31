package com.baeldung.multireleaseapp;

public class App {
    public static void main(String[] args) throws Exception {
        String dateToCheck = "2012-09-22";
        boolean isLeapYear = DateHelper.checkIfLeapYear(dateToCheck);
        System.out.println("Date given " + dateToCheck + " is leap year: " + isLeapYear);
    }
}
