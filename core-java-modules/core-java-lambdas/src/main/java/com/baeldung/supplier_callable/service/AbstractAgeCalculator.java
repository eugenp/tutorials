package com.baeldung.supplier_callable.service;

import java.time.LocalDate;
import java.time.Period;

public abstract class AbstractAgeCalculator {

    protected Integer calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now())
          .getYears();
    }
}
