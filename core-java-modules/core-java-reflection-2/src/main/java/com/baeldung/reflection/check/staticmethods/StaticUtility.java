package com.baeldung.reflection.check.staticmethods;

import java.time.LocalDate;
import java.time.LocalTime;

public class StaticUtility {

    public static String getAuthorName() {
        return "Umang Budhwar";
    }

    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }
}
