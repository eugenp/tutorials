package com.baeldung.jsonargs;

public class AddressDto {

    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String country;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" + "streetName='" + streetName + '\'' + ", streetNumber='" + streetNumber + '\'' + ", postalCode='" + postalCode + '\'' + ", city='" + city + '\'' + ", country='" + country + '\'' + '}';
    }
}
