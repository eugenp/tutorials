package com.baeldung.deepcopy;

import java.io.Serializable;

class User implements Serializable, Cloneable {

    private static final long serialVersionUID = -3427002229954777557L;
    private String firstName;
    private String lastName;
    private Address address;

    public User(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public User(User that) {
        this(that.getFirstName(), that.getLastName(), new Address(that.getAddress()));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        user.address = (Address) this.address.clone();
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }
}
