package com.baeldung.cloning;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable {

    private String code;
    private String name;
    private Currency currency;

    public Country(String code, String name, Currency currency) {
        this.code = code;
        this.name = name;
        this.currency = currency;
    }

    public Country(Country that) {
        this(that.code, that.name, new Currency(that.currency));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    protected Country clone() throws CloneNotSupportedException {
        try {
            return (Country) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Country(this.code, this.name, new Currency(this.currency.getCode(), this.currency.getSymbol(), this.currency.getValue()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        Country country = (Country) o;
        return Objects.equals(getCode(), country.getCode()) && Objects.equals(getName(), country.getName()) &&
            Objects.equals(getCurrency(), country.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getName(), getCurrency());
    }
}
