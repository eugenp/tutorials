package com.baeldung.corecopy;

public class User {
    private String name;
    private Address address;

    public User(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public User(User that) {
        this(that.getName(), new Address(that.getAddress()));
    }

    public User() {
    }

    @Override
    public Object clone() {
        User user;
        try {
            user = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            user = new User(this.getName(), this.getAddress());
        }
        user.address = (Address) this.address.clone();
        return user;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

}
