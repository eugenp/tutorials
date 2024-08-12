package com.baeldung.shallowdeepcopy;

class AddressShallowCopy {
    String city;

    AddressShallowCopy(String city) {
        this.city = city;
    }
}

class PersonShallowCopy implements Cloneable {
    String name;
    AddressShallowCopy address;

    PersonShallowCopy(String name, AddressShallowCopy address) {
        this.name = name;
        this.address = address;
    }

    // Creates a shallow copy of the Person object
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Shallow copy
    }
}

