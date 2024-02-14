package com.baeldung.deepshallowcopy;

public class AddressDeepCopy implements Cloneable {
    private String country;

    public AddressDeepCopy(String country) {
        this.country = country;
    }

    // Setter and Getter
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}