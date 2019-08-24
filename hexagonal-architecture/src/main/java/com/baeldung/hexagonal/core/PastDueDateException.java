package com.baeldung.hexagonal.core;

import java.time.LocalDate;

public class PastDueDateException extends TodoException {

    private LocalDate currentDate;

    private LocalDate enteredDueDate;

    public PastDueDateException(LocalDate currentDate, LocalDate enteredDueDate) {
        this.currentDate = currentDate;
        this.enteredDueDate = enteredDueDate;
    }
}
