package com.baeldung.sampleapp.web.dto;


public class HeavyResourceAddressPartialUpdate {
    private Integer id;
    private String address;

    public HeavyResourceAddressPartialUpdate() {
    }

    public HeavyResourceAddressPartialUpdate(Integer id, String address) {
        this.id = id;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
