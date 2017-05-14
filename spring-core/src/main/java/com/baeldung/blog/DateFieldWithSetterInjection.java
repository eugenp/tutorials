package com.baeldung.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class DateFieldWithSetterInjection {

    private DateTimeFormatter formatter;

    @Autowired
    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter);
    }

    @Override
    public String toString() {
        return LocalDate.now().format(this.formatter);
    }
}
