package com.baeldung.instancio.student.model;

import com.baeldung.instancio.util.PrettyToString;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private String title;
    private String code;
    private LocalDate startDate;
    private Duration duration;
    private String instructor;

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        final Course course = (Course) o;
        return Objects.equals(getCode(), course.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
