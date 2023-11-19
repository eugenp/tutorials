package com.baeldung.accesing_session_attributes.business.entities;

import java.util.List;

/**
 * https://api.nationalize.io/?name=michael
 */
public class NameCountriesEntity {
    private String name;
    private List<NameCountryEntity> country;

    public NameCountriesEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NameCountryEntity> getCountry() {
        return country;
    }

    public void setCountry(List<NameCountryEntity> country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NameCountriesEntity other = (NameCountriesEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NameCountriesModel [name=" + name + ", country=" + country + "]";
    }

}
