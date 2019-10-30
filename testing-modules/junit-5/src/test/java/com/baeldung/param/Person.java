package com.baeldung.param;

/**
 * Very simple Person entity.
 * Use the Fluent-style interface to set properties.
 * 
 * @author J Steven Perry
 *
 */
public class Person {

    private Long id;
    private String lastName;
    private String firstName;

    public Long getId() {
        return id;
    }

    public Person setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

}
