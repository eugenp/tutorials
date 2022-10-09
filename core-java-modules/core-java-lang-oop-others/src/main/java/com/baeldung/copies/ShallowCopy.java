package com.baeldung.copies;

import javax.annotation.Nonnull;

final class ShallowCopy implements Cloneable {

    private final Person person;

    ShallowCopy(@Nonnull final Person person) {
        this.person = person;
    }

    ShallowCopy(@Nonnull final ShallowCopy original) {
        this.person = original.getPerson();
    }

    Person getPerson() {
        return this.person;
    }

    @Override
    public ShallowCopy clone() {
        try {
            return (ShallowCopy) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}