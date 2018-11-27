package com.baeldung.hexagonal;

public class EmployeeDomainObject {
    public String name;
    public String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Name = " + name + ":: Address = " + address;
    }

}
