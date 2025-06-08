package com.baeldung.suppliercallable.service.callable;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.Callable;

public class AgeCalculatorCallable implements Callable<Integer> {

    private final LocalDate birthDate;

    @Override
    public Integer call() throws Exception {
        return Period.between(birthDate, LocalDate.now())
          .getYears();
    }

    public AgeCalculatorCallable(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
