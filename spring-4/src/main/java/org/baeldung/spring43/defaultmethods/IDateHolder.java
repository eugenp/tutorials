package org.baeldung.spring43.defaultmethods;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface IDateHolder {

    LocalDate getLocalDate();

    void setLocalDate(LocalDate localDate);

    default void setStringDate(String stringDate) {
        setLocalDate(LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

}
