package com.baeldung.skipfields;

public class Person implements Hidable {
    private String name;
    private Address address;
    private boolean hidden;

    public Person(final String name, final Address address, final boolean hidden) {
        super();
        this.name = name;
        this.address = address;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

}
