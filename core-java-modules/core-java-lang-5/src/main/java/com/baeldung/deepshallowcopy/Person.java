package com.baeldung.deepshallowcopy;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class Person implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    Calendar birthdate;

    public Person() {
    }

    public Person(String name, Calendar birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return Objects.equals(birthdate, other.birthdate) && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthdate, name);
    }
}
