package org.baeldung.javaxval.methodvalidation.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
public class Customer {

    @Size(min = 5, max = 200)
    private String firstName;

    @Size(min = 5, max = 200)
    private String lastName;

    public Customer(@Size(min = 5, max = 200) @NotNull String firstName, @Size(min = 5, max = 200) @NotNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer() {

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
