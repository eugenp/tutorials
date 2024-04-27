package com.baeldung.map.castingmaptoobject;

public class Address {
    private String city;
    private Country country;

    public Address() {
        // default constructor needed for Jackson deserialization
    }

    public Address(String city, Country country) {
        this.city = city;
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!city.equals(address.city)) return false;
        return country.getName().equals(address.country.getName());
    }

}
