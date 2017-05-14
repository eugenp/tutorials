package com.baeldung.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class DateFieldWithConstructorInjection {

    private final DateTimeFormatter formatter;

    @Autowired
    public DateFieldWithConstructorInjection(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "a formatter is required to initialize a date field");
    }

    @Override
    public String toString() {
        return LocalDate.now().format(this.formatter);
    }
}
