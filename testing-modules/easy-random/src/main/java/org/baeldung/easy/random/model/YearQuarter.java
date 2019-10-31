package org.baeldung.easy.random.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class YearQuarter {

    private LocalDate startDate;
    private LocalDate endDate;

    public YearQuarter(LocalDate startDate) {
        this.startDate = startDate;
        autoAdjustEndDate();
    }

    private void autoAdjustEndDate() {
        endDate = startDate.plusMonths(3L);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        YearQuarter quarter = (YearQuarter) o;
        return Objects.equals(startDate, quarter.startDate) && Objects.equals(endDate, quarter.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", YearQuarter.class.getSimpleName() + "[", "]").add("startDate=" + startDate)
            .add("endDate=" + endDate)
            .toString();
    }
}
