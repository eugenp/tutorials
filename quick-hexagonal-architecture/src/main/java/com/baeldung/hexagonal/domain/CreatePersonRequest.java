package com.baeldung.hexagonal.domain;

import java.util.Objects;

public class CreatePersonRequest {

    private String first;
    private String last;

    public CreatePersonRequest(String first, String last) {
        this.first = Objects.requireNonNull(first);
        this.last = Objects.requireNonNull(last);
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePersonRequest)) {
            return false;
        }
        CreatePersonRequest that = (CreatePersonRequest) o;
        return first.equals(that.first) && last.equals(that.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }
}
