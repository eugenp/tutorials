package com.baeldung.deepshallowcopy.cloneable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student implements Cloneable {

    private String name;
    private String batch;
    private String department;
    private Address address;

    @Override
    public Student clone() {

        try {
            Student clone = (Student) super.clone();
            clone.setAddress((Address) this.address.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
