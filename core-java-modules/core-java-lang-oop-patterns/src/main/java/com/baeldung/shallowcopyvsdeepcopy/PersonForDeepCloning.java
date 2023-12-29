package com.baeldung.shallowcopyvsdeepcopy;

import java.io.Serializable;

public class PersonForDeepCloning implements Cloneable, Serializable {

    private Address address;

    @Override
    public PersonForDeepCloning clone() {
        try {
            PersonForDeepCloning person = (PersonForDeepCloning) super.clone();
            person.setAddress(person.getAddress()
                .clone());
            return person;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static class Address implements Cloneable, Serializable {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public Address clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
