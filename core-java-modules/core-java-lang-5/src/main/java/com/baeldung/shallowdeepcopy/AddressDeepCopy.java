package com.baeldung.shallowdeepcopy;

class AddressDeepCopy {
    String city;

    AddressDeepCopy(String city) {
        this.city = city;
    }

    protected AddressDeepCopy clone() {
        return new AddressDeepCopy(this.city);
    }
}