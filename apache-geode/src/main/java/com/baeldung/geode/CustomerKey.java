package com.baeldung.geode;

import java.io.Serializable;

public class CustomerKey implements Serializable {

    private static final long serialVersionUID = -3529253035303792458L;
    private long id;
    private String country;

    public CustomerKey(long id) {
        this.id = id;
        this.country = "USA";
    }

    public CustomerKey(long id, String country) {
        this.id = id;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomerKey that = (CustomerKey) o;

        if (id != that.id)
            return false;
        return country != null ? country.equals(that.country) : that.country == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
