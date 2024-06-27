package com.baeldung.ShallowAndDeepCopyUnitTest;

class Person implements Cloneable {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected Person deepCopy() {
        return new Person(this.name, new Address(this.address.city));
    }

}
