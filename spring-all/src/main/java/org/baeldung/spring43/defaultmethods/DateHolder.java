package org.baeldung.spring43.defaultmethods;

import java.time.LocalDate;

public class DateHolder implements IDateHolder {

    private LocalDate localDate;

    @Override
    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
