package com.baeldung.deepvsshallowcopy;

public class Author {

    private String firstName;
    private String lastName;

    public Author(Author author) {
        this(author.getFirstName(), author.getLastName());
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

}
