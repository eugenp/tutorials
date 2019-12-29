package com.baeldung.elementcollection.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Phone {
    private String type;
    private String areaCode;
    private String number;

    public Phone() {
    }

    public Phone(String type, String areaCode, String number) {
        this.type = type;
        this.areaCode = areaCode;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phone)) {
            return false;
        }
        Phone phone = (Phone) o;
        return getType().equals(phone.getType()) &&
                getAreaCode().equals(phone.getAreaCode()) &&
                getNumber().equals(phone.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getAreaCode(), getNumber());
    }
}
