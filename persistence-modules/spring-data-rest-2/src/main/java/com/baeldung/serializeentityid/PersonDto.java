package com.baeldung.serializeentityid;

public class PersonDto {

    private final Long id;

    private final String name;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
