package com.baeldung.shallowdeepcopy;

class AddressDeepCopy {
    String city;

    AddressDeepCopy(String city) {
        this.city = city;
    }

    protected AddressDeepCopy clone() {
        // Strings are immutable in Java, so copying them (deep or shallow) results in new objects
        return new AddressDeepCopy(this.city);
    }
}

class PersonDeepCopy implements Cloneable {
    String name;
    AddressDeepCopy address;

    PersonDeepCopy(String name, AddressDeepCopy address) {
        this.name = name;
        this.address = address;
    }

    // Creates a deep copy of the Person object
    @Override
    protected PersonDeepCopy clone() {
        return new PersonDeepCopy(this.name, this.address.clone());
    }
}
