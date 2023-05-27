package com.baeldung.accesing_session_attributes.business.entities;

/**
 * NameCountryModel
 * 
 * https://www.countryflagicons.com/
 * <img src="https://www.countryflagicons.com/STYLE/size/COUNTRYCODE.png">
 * STYLE: FLAT, SHINY
 * size: 16, 24, 32, 48, 64
 * COUNTRYCODE: country_id
 */
public class NameCountryEntity {
    private String country_id;
    private Float probability;

    public NameCountryEntity() {
        super();
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country_id == null) ? 0 : country_id.hashCode());
        result = prime * result + ((probability == null) ? 0 : probability.hashCode());
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
        NameCountryEntity other = (NameCountryEntity) obj;
        if (country_id == null) {
            if (other.country_id != null)
                return false;
        } else if (!country_id.equals(other.country_id))
            return false;
        if (probability == null) {
            if (other.probability != null)
                return false;
        } else if (!probability.equals(other.probability))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NameCountryModel [country_id=" + country_id + ", probability=" + probability + "]";
    }

}
