package com.baeldung.criteria;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "school")
public class School {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public School() {
    }

    public School(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School school)) {
            return false;
        }
        return id == school.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
