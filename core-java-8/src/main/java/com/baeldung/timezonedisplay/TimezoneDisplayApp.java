package com.baeldung.timezonedisplay;

import java.util.List;

public class TimezoneDisplayApp {

    public static void main(String... args) {
        TimezoneDisplay display = new TimezoneDisplay();

        System.out.println("Time zones in UTC:");
        List<String> utc = display.compileListFor(TimezoneDisplay.OffsetBase.UTC);
        utc.forEach(System.out::println);

        System.out.println("Time zones in GMT:");
        List<String> gmt = display.compileListFor(TimezoneDisplay.OffsetBase.GMT);
        gmt.forEach(System.out::println);
    }

}
