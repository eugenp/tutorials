package com.baeldung.printf;

import java.util.Date;

public class PrintfExamples {

    public static void main(String[] args) {

        printfDateTime();
        //printfBoolean();
    }

    private static void printfBoolean() {
        System.out.printf("%b%n", null);
        System.out.printf("%b%n", false);
        System.out.printf("%B%n", false);
    }

    private static void printfDateTime() {
        Date date = new Date();
        System.out.printf("%tT%n", date);
        System.out.printf("hours %tH: minutes %tM: seconds %tS%n", date, date, date);
        System.out.printf("%1$tH:%1$tM:%1$tS %1$tp %1$tL %1$tN %1$tz %n", date);

        System.out.printf("%1$tA %1$tB %1$tY %n", date);
        System.out.printf("%1$td.%1$tm.%1$ty %n", date);
    }
}
