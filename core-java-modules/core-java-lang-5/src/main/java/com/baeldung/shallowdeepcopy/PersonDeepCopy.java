package com.baeldung.shallowdeepcopy;

class PersonDeepCopy implements Cloneable {
    String name;
    AddressDeepCopy address;

    PersonDeepCopy(String name, AddressDeepCopy address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected PersonDeepCopy clone() {
        return new PersonDeepCopy(this.name, this.address.clone());
    }
}
