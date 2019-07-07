package org.baeldung.easy.random.randomizer;

import org.baeldung.easy.random.model.YearQuarter;
import org.jeasy.random.api.Randomizer;

import java.time.LocalDate;
import java.time.Month;

public class YearQuarterRandomizer implements Randomizer<YearQuarter> {

    private LocalDate date = LocalDate.of(1990, Month.SEPTEMBER, 25);

    @Override
    public YearQuarter getRandomValue() {
        date = date.plusMonths(3);
        return new YearQuarter(date);
    }
}
