package com.baeldung.setnullproperty.entity;

import java.util.Objects;

public class WeeklyNews extends Reviewable {

    public WeeklyNews() {
    }

    public WeeklyNews(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeeklyNews that = (WeeklyNews) o;
        return Objects.equals(reviewedBy, that.reviewedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reviewedBy);
    }
}
