package com.baeldung.enummap;

public class SandboxUtil {
    public static void main(String[] args) {
//        for (int i = 0; i < 1000; i++) {
//            String formatted = String.format("CCC_%03d", i);
//            System.out.print(formatted + ",");
//            if (i % 10 == 0) System.out.println();
//        }
        System.out.printf ("%-10s %-30s %n", "Monday", "Soccer");
        System.out.printf ("%-10s %-30s %n", "Tuesday", "Basketball");
        System.out.printf ("%-10s %-30s %n", "Wednesday", "Hiking");
        System.out.printf ("%-10s %-30s %n", "Thursday", "Karate");
        System.out.printf ("%-10s %-30s %n", "Friday", "Swimming");
        System.out.printf ("%-10s %-30s %n", "Saturday", "Rest");
        System.out.printf ("%-10s %-30s %n", "Sunday", "Rest");
    }
}
