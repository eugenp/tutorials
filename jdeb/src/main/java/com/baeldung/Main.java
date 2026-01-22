package com.baeldung;

import java.time.LocalDate;
import java.time.YearMonth;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        YearMonth ym = YearMonth.from(today);
        int days = ym.lengthOfMonth();
        int start = ym.atDay(1).getDayOfWeek().getValue();

        System.out.println("Mo Tu We Th Fr Sa Su");
        for (int i = 1; i < start; i++) System.out.print("   ");
        for (int d = 1; d <= days; d++) {
            String out = (d == today.getDayOfMonth()) ? + d + "*" : String.format("%2d", d);
            System.out.print(out + " ");
            if ((d + start - 1) % 7 == 0) System.out.println();
        }
    }
}
