package com.baeldung.deepshallowcopy;

// Immutable class example
final class ImmutablePerson {
    private final String name;
    private final Address address;

    public ImmutablePerson(String name, Address address) {
        this.name = name;
        this.address = new Address(address.getStreet());
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return new Address(address.getStreet());
    }

    @Override
    public String toString() {
        return name + ", " + address;
    }
}