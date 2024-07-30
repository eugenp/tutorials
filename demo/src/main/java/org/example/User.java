package org.example;

public class User {

    private String firstName;
    private String lastName;
    private Address address;

    // constructor, getters, setters
    public User() {

    }

    public User(String firstName, String lastName, Address address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public User(User user) {
        this(user.getFirstName(), user.getLastName(), new Address(user.getAddress()));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
