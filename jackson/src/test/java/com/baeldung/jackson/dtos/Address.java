package com.baeldung.jackson.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Address {

    @JacksonXmlProperty(localName = "street_number")
    String streetNumber;

    @JacksonXmlProperty(localName = "street_name")
    String streetName;

    @JacksonXmlProperty(localName = "city")
    String city;

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
