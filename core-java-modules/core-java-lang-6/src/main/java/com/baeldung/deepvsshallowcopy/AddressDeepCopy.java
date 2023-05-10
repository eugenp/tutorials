package com.baeldung.deepvsshallowcopy;

public class AddressDeepCopy implements Cloneable {
    String street;
    String city;
    String state;

    public AddressDeepCopy(String street, String city, String state)
    {
        this.street=street;
        this.city=city;
        this.state=state;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}