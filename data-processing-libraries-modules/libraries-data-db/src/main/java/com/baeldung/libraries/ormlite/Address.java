package com.baeldung.libraries.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "addresses")
public class Address {
    @DatabaseField(generatedId = true)
    private long addressId;

    @DatabaseField(canBeNull = false)
    private String addressLine;

    public Address() {
    }

    public Address(String addressLine) {
        this.addressLine = addressLine;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

}
