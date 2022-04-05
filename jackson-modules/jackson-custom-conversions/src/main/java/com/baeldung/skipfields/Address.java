package com.baeldung.skipfields;

public class Address implements Hidable {
    private String city;
    private String country;
    private boolean hidden;

    public Address(final String city, final String country, final boolean hidden) {
        super();
        this.city = city;
        this.country = country;
        this.hidden = hidden;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

}
