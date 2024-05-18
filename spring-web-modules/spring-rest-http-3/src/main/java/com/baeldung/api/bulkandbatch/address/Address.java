package com.baeldung.api.bulkandbatch.address;

public class Address {

    private int id;
    private String addressLn1;
    private String city;
    private int pinCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressLn1() {
        return addressLn1;
    }

    public void setAddressLn1(String addressLn1) {
        this.addressLn1 = addressLn1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
}
