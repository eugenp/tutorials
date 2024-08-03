package com.baeldung.deepshallowcopy.cloneable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person implements Cloneable {
    private String name;
    private Address address;

    @Override
    public Person clone() {
        try {
            Person clone = (Person) super.clone();
            clone.setAddress(this.address.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}