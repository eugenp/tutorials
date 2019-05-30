package com.baeldung.hexagonal.web;

import java.util.Objects;

/**
 * Person value object for representing the JSON response from the Person resource.
 */
final class PersonDTO {

    private final int id;
    private final String first;
    private final String last;

    PersonDTO(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public int getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id && Objects.equals(first, personDTO.first) && Objects.equals(last, personDTO.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first, last);
    }
}
