package com.baeldung.javaxval.container.validation;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class Customer {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private List<@NotBlank(message = "Address must not be blank") String> addresses;

    private Integer age;

    @PositiveOrZero
    private OptionalInt numberOfOrders;

    private Profile profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public Optional<@Min(18) Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public OptionalInt getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(OptionalInt numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
