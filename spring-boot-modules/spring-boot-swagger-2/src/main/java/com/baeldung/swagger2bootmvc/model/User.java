package com.baeldung.swagger2bootmvc.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class User {

    @Schema(description = "first name of the user", name = "firstName", type = "string", example = "Vatsal")
    String firstName;

    public User() {
        super();
    }

    public User(final String firstName) {
        super();
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
