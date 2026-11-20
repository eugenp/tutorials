package com.baeldung.multiparameterconstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Currency {
    EUR("Euro", "cent"),
    GBP("Pound sterling", "penny"),
    CHF("Swiss franc", "Rappen");

    private String fullName;
    private String fractionalUnit;

    Currency(String fullName, String fractionalUnit) {
        this.fullName = fullName;
        this.fractionalUnit = fractionalUnit;
    }

    @JsonCreator
    public static Currency fromJsonString(String fullName, String fractionalUnit) {
        for (Currency c : Currency.values()) {
            if (c.fullName.equalsIgnoreCase(fullName) && c.fractionalUnit.equalsIgnoreCase(fractionalUnit)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown currency: " + fullName + " " + fractionalUnit);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getfractionalUnit() {
        return fractionalUnit;
    }

    public void setfractionalUnit(String fractionalUnit) {
        this.fractionalUnit = fractionalUnit;
    }
}

