package com.baeldung.printf;

import java.util.Date;
import java.util.Locale;

public class PrintfExamples {

    public static void main(String[] args) {

        printfChar();
        printfNumber();
        printfDateTime();
        printfBoolean();

        System.out.printf("baeldung%nline%nterminator");
    }

    private static void printfChar() {
        System.out.printf("%c%n", 's');
        System.out.printf("%C%n", 's');
    }

    private static void printfNumber() {

        System.out.printf("simple integer: %d%n", 10000L);

        System.out.printf(Locale.US, "%,d %n", 10000);
        System.out.printf(Locale.ITALY, "%,d %n", 10000);

        System.out.printf("%f%n", 5.1473);
        System.out.printf("<%5.2f>%n", 5.1473);
        System.out.printf("<%5.2e>%n", 5.1473);
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
