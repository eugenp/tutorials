package com.baeldung.libraries.smooks.model;

public class Supplier {

    private String name;
    private String phoneNumber;

    public Supplier() {
    }

    public Supplier(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Supplier supplier = (Supplier) o;

        if (name != null ? !name.equals(supplier.name) : supplier.name != null)
            return false;
        return phoneNumber != null ? phoneNumber.equals(supplier.phoneNumber) : supplier.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
