package com.baeldung.shallowndeepcopy;

public class AddressDeepCopy implements Cloneable {

    public String city;

    public AddressDeepCopy(String city) {
        super();
        this.city = city;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
