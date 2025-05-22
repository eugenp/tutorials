package com.baeldung.hibernate.union.dto;

import java.util.Objects;

public class PersonDto {

    private Long id;
    private String name;
    private String entity;

    public PersonDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PersonDto(Long id, String name, String entity) {
        this(id, name);
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "PersonDto {id: " + id + ", name: " + name + ", entity: " + entity + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonDto other = (PersonDto) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }
}
