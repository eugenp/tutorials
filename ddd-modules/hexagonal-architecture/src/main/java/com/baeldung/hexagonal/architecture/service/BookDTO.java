package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.repository.Book;

import java.util.Objects;

public class BookDTO {
    private Integer id;
    private String name;
    private Integer shelfNo;

    public BookDTO() {}

    public BookDTO(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.shelfNo = book.getShelfNo();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShelfNo(Integer shelfNo) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(id, bookDTO.id) && Objects.equals(name, bookDTO.name) && Objects.equals(shelfNo, bookDTO.shelfNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shelfNo);
    }
}
