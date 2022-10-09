package com.baeldung.copies;

import javax.annotation.Nonnull;

final class DeepCopy implements Cloneable {

    private Person person;

    DeepCopy(@Nonnull final Person person) {
        this.person = new Person(person.getName(), person.getAge());
    }

    DeepCopy(@Nonnull final DeepCopy original) {
        this.person = new Person(original.getPerson()
          .getName(), original.getPerson()
          .getAge());
    }

    Person getPerson() {
        return this.person;
    }

    @Override
    @Nonnull
    public DeepCopy clone() {
        try {
            final DeepCopy deepCopy = (DeepCopy) super.clone();
            deepCopy.person = new Person(this.getPerson()
              .getName(), this.getPerson()
              .getAge());
            return deepCopy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}