package com.baeldung.timezonedisplay;

import java.util.List;

public class TimezoneDisplayJava7App {

    public static void main(String... args) {
        TimezoneDisplayJava7 display = new TimezoneDisplayJava7();

        System.out.println("Time zones in UTC:");
        List<String> utc = display.getTimeZoneList(TimezoneDisplayJava7.OffsetBase.UTC);
        for (String timeZone : utc) {
            System.out.println(timeZone);
        }

        System.out.println("Time zones in GMT:");
        List<String> gmt = display.getTimeZoneList(TimezoneDisplayJava7.OffsetBase.GMT);
        for (String timeZone : gmt) {
            System.out.println(timeZone);
        }
    }

}
