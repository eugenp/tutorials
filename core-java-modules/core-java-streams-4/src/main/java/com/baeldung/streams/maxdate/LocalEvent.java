package com.baeldung.streams.maxdate;

import java.time.LocalDate;

public class LocalEvent {

    LocalDate date;

    public LocalEvent(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
