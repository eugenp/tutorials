package com.baeldung.nullconversion;

public class Address implements Cloneable {

    private ZipCode zipCode;

    public ZipCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    protected Address clone()  {
        Address address;
        try {
            address = ((Address) super.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        zipCode = zipCode.clone();
        return address;
    }
}
