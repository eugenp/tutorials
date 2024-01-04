package com.baeldung.jsonignore.emptyfields;

public class PhoneNumber {

    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(final String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
               "number='" + number + '\'' +
               '}';
    }
}
