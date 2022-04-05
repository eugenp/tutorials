package com.baeldung.reflection.check.abstractclass;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AbstractExample {

    public static String getAuthorName() {
        return "Umang Budhwar";
    }

    public abstract LocalDate getLocalDate();

    public abstract LocalTime getLocalTime();
}
