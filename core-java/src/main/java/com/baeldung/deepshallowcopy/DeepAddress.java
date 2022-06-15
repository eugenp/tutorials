package com.baeldung.deepshallowcopy;

public class DeepAddress implements Cloneable {
    String street;
    String city;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public DeepAddress(String street, String city) {
        this.street = street;
        this.city = city;
    }
}
