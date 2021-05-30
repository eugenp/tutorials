package com.baeldung.hexarch.boostrore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@Entity(name = "books")  @Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String isbn;
    @OneToMany
    private Set<Author> authors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

