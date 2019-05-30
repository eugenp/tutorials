package com.baeldung.hexagonal.fake;

import com.baeldung.hexagonal.domain.Person;

class FakePerson implements Person {

    private final int id;
    private final String first;
    private final String last;

    FakePerson(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirst() {
        return first;
    }

    @Override
    public String getLast() {
        return last;
    }
}
