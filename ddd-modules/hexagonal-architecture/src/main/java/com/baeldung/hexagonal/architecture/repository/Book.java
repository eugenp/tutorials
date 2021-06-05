package com.baeldung.hexagonal.architecture.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    private Integer id;
    private String name;
    private Integer shelfNo;

    public Book(Integer id, String name, Integer shelfNo) {
        this.id = id;
        this.name = name;
        this.shelfNo = shelfNo;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getShelfNo() {
        return shelfNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(shelfNo, book.shelfNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shelfNo);
    }
}
