package com.baeldung.rce;

import java.util.Objects;

/** Person model */
public final class Person {

    private String first;
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(first, person.first) && Objects.equals(last, person.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }
}
