package com.baeldung.shallowdeepcopy;

class PersonShallowCopy implements Cloneable {
    String name;
    AddressShallowCopy address;

    PersonShallowCopy(String name, AddressShallowCopy address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}