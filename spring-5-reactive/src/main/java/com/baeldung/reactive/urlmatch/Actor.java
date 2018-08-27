package com.baeldung.reactive.urlmatch;

class Actor {
    private String firstname;
    private String lastname;

    public Actor() {
    }

    public Actor(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
