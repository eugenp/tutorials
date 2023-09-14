package com.baeldung.jsonschemageneration.plugin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Person {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID id;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    @Email @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@baeldung\\.com\\b") String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)

    String surname;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE, required = true)
    Address address;

    @Null String fullName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Date createdAt;

    @Size(max = 10)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    List<Person> friends;

}

class Address {

    @Null
    @JsonProperty
    String street;

    @NotNull
    @JsonProperty(required = true)
    String city;

    @NotNull
    @JsonProperty(required = true)
    String country;
}