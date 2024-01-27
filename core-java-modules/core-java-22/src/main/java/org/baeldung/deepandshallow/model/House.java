package org.baeldung.deepandshallow.model;

public class House {

    public House() {
    }

    /**
     Copy constructor
     */
    public House(House house) {
        this.setAddress(house.getAddress());
    }

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
