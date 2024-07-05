package com.baeldung;

import java.util.Objects;

class User implements Cloneable {
    String name;
    int age;
    String email;
    Address address;

    public User(String name, int age, String email, Address address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        User cloned = (User) super.clone();
        cloned.name = new String(name);
        cloned.email = new String(email);
        cloned.address = (Address) address.clone();
        return cloned;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + ", email='" + email + "', address=" + address + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(address, user.address);
    }
}
